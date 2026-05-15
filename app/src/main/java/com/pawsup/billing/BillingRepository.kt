package com.pawsup.billing

import com.android.billingclient.api.Purchase
import com.pawsup.data.UserPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillingRepository @Inject constructor(
    private val billingClient: BillingClientWrapper,
    private val prefs: UserPreferences
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val entitlements: Flow<Entitlements> = combine(
        prefs.ownedCatIds,
        prefs.hasCafeBundle,
        prefs.entitlementsLastSynced
    ) { owned, bundle, synced ->
        val offline = Entitlements.offlineFallback(synced)
        offline ?: Entitlements(owned, bundle, synced)
    }

    init {
        scope.launch {
            billingClient.events.collect { event ->
                when (event) {
                    is BillingEvent.Purchased -> handlePurchase(event.productId)
                    else -> Unit
                }
            }
        }
    }

    suspend fun refreshPurchases() {
        val purchases = billingClient.queryPurchases()
        syncPurchasesToPrefs(purchases)
    }

    private suspend fun syncPurchasesToPrefs(purchases: List<Purchase>) {
        val owned = mutableSetOf("miso")
        var hasBundle = false
        purchases.filter { it.purchaseState == Purchase.PurchaseState.PURCHASED }.forEach { p ->
            p.products.forEach { productId ->
                when (productId) {
                    ProductIds.CAFE_PRODUCT -> hasBundle = true
                    else -> ProductIds.PER_CAT.entries
                        .firstOrNull { it.value.productId == productId }
                        ?.let { owned.add(it.key) }
                }
            }
        }
        prefs.setOwnedCatIds(owned)
        prefs.setHasCafeBundle(hasBundle)
        prefs.setEntitlementsLastSynced(System.currentTimeMillis())
    }

    private suspend fun handlePurchase(productId: String) {
        when (productId) {
            ProductIds.CAFE_PRODUCT -> {
                prefs.setHasCafeBundle(true)
                prefs.setEntitlementsLastSynced(System.currentTimeMillis())
            }
            else -> {
                val catId = ProductIds.PER_CAT.entries
                    .firstOrNull { it.value.productId == productId }?.key ?: return
                val current = prefs.snapshotOwnedCatIds().toMutableSet()
                current.add(catId)
                prefs.setOwnedCatIds(current)
                prefs.setEntitlementsLastSynced(System.currentTimeMillis())
            }
        }
    }
}
