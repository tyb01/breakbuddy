package com.pawsup.billing

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

sealed class BillingEvent {
    data class Purchased(val productId: String, val purchaseToken: String) : BillingEvent()
    data class Error(val code: Int, val message: String) : BillingEvent()
    object UserCancelled : BillingEvent()
}

@Singleton
class BillingClientWrapper @Inject constructor(
    @ApplicationContext private val context: Context
) : PurchasesUpdatedListener {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _events = MutableSharedFlow<BillingEvent>(extraBufferCapacity = 16)
    val events: SharedFlow<BillingEvent> = _events.asSharedFlow()

    private val _productDetails = mutableMapOf<String, ProductDetails>()
    val productDetails: Map<String, ProductDetails> get() = _productDetails

    private var retryDelay = 1_000L

    val client: BillingClient = BillingClient.newBuilder(context)
        .setListener(this)
        .enablePendingPurchases(
            PendingPurchasesParams.newBuilder().enableOneTimeProducts().build()
        )
        .build()

    init {
        connect()
    }

    private fun connect() {
        client.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(result: BillingResult) {
                if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                    retryDelay = 1_000L
                    scope.launch { queryProductDetails() }
                    scope.launch { queryPurchases() }
                } else {
                    scheduleReconnect()
                }
            }

            override fun onBillingServiceDisconnected() {
                scheduleReconnect()
            }
        })
    }

    private fun scheduleReconnect() {
        scope.launch {
            delay(retryDelay)
            retryDelay = (retryDelay * 2).coerceAtMost(30_000L)
            connect()
        }
    }

    private suspend fun queryProductDetails() {
        val productList = buildList {
            ProductIds.PER_CAT.values.forEach { sub ->
                add(QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(sub.productId)
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build())
            }
            add(QueryProductDetailsParams.Product.newBuilder()
                .setProductId(ProductIds.CAFE_PRODUCT)
                .setProductType(BillingClient.ProductType.SUBS)
                .build())
        }
        val params = QueryProductDetailsParams.newBuilder().setProductList(productList).build()
        val result = client.queryProductDetails(params)
        if (result.billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            result.productDetailsList?.forEach { _productDetails[it.productId] = it }
        }
    }

    suspend fun queryPurchases(): List<Purchase> {
        if (!client.isReady) return emptyList()
        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS)
            .build()
        val result = client.queryPurchasesAsync(params)
        if (result.billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            result.purchasesList.forEach { acknowledgePurchaseIfNeeded(it) }
            return result.purchasesList
        }
        return emptyList()
    }

    fun launchBillingFlow(activity: Activity, productId: String, basePlanId: String): Boolean {
        val details = _productDetails[productId] ?: return false
        val offer = details.subscriptionOfferDetails
            ?.firstOrNull { it.basePlanId == basePlanId } ?: return false
        val params = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(listOf(
                BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(details)
                    .setOfferToken(offer.offerToken)
                    .build()
            ))
            .build()
        client.launchBillingFlow(activity, params)
        return true
    }

    private suspend fun acknowledgePurchaseIfNeeded(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged) {
            val params = AcknowledgePurchaseParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()
            client.acknowledgePurchase(params)
        }
    }

    override fun onPurchasesUpdated(result: BillingResult, purchases: List<Purchase>?) {
        when (result.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                purchases?.forEach { purchase ->
                    if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                        scope.launch { acknowledgePurchaseIfNeeded(purchase) }
                        purchase.products.forEach { productId ->
                            scope.launch {
                                _events.emit(BillingEvent.Purchased(productId, purchase.purchaseToken))
                            }
                        }
                    }
                }
            }
            BillingClient.BillingResponseCode.USER_CANCELED ->
                scope.launch { _events.emit(BillingEvent.UserCancelled) }
            else ->
                scope.launch { _events.emit(BillingEvent.Error(result.responseCode, result.debugMessage)) }
        }
    }
}
