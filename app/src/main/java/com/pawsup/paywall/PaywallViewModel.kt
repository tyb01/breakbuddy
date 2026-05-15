package com.pawsup.paywall

import android.app.Activity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.ProductDetails
import com.pawsup.billing.BillingClientWrapper
import com.pawsup.billing.BillingRepository
import com.pawsup.billing.ProductIds
import com.pawsup.cats.Cat
import com.pawsup.cats.CatRegistry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class PricePeriod { MONTHLY, YEARLY }

data class PaywallUiState(
    val cat: Cat? = null,
    val period: PricePeriod = PricePeriod.YEARLY,
    val catPrice: String = "",
    val cafePrice: String = "",
    val purchaseSuccess: Boolean = false,
    val isLoading: Boolean = false
)

@HiltViewModel
class PaywallViewModel @Inject constructor(
    private val catRegistry: CatRegistry,
    private val billingClient: BillingClientWrapper,
    private val billingRepo: BillingRepository,
    savedState: SavedStateHandle
) : ViewModel() {

    private val catId: String = savedState[PaywallActivity.EXTRA_CAT_ID] ?: "yuki"

    private val _state = MutableStateFlow(PaywallUiState())
    val state: StateFlow<PaywallUiState> = _state.asStateFlow()

    init {
        val cat = catRegistry.find(catId)
        _state.update { it.copy(cat = cat) }
        updatePrices(PricePeriod.YEARLY)

        viewModelScope.launch {
            billingClient.events.collect { event ->
                when (event) {
                    is com.pawsup.billing.BillingEvent.Purchased -> {
                        billingRepo.refreshPurchases()
                        _state.update { it.copy(purchaseSuccess = true, isLoading = false) }
                    }
                    else -> _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun setPeriod(period: PricePeriod) {
        _state.update { it.copy(period = period) }
        updatePrices(period)
    }

    private fun updatePrices(period: PricePeriod) {
        val perCat = ProductIds.PER_CAT[catId]
        val catDetails = perCat?.let { billingClient.productDetails[it.productId] }
        val cafeDetails = billingClient.productDetails[ProductIds.CAFE_PRODUCT]

        val planId = if (period == PricePeriod.MONTHLY) perCat?.monthlyPlan else perCat?.yearlyPlan
        val cafePlanId = if (period == PricePeriod.MONTHLY) ProductIds.CAFE_MONTHLY else ProductIds.CAFE_YEARLY

        val catPrice = catDetails?.priceForPlan(planId) ?: if (period == PricePeriod.MONTHLY) "$1.99/mo" else "$9.99/yr"
        val cafePrice = cafeDetails?.priceForPlan(cafePlanId) ?: if (period == PricePeriod.MONTHLY) "$4.99/mo" else "$24.99/yr"

        _state.update { it.copy(catPrice = catPrice, cafePrice = cafePrice) }
    }

    fun adoptCat(activity: Activity) {
        val perCat = ProductIds.PER_CAT[catId] ?: return
        val planId = if (_state.value.period == PricePeriod.MONTHLY) perCat.monthlyPlan else perCat.yearlyPlan
        _state.update { it.copy(isLoading = true) }
        billingClient.launchBillingFlow(activity, perCat.productId, planId)
    }

    fun adoptCafe(activity: Activity) {
        val planId = if (_state.value.period == PricePeriod.MONTHLY) ProductIds.CAFE_MONTHLY else ProductIds.CAFE_YEARLY
        _state.update { it.copy(isLoading = true) }
        billingClient.launchBillingFlow(activity, ProductIds.CAFE_PRODUCT, planId)
    }

    private fun ProductDetails.priceForPlan(planId: String?): String? {
        val offer = subscriptionOfferDetails?.firstOrNull { it.basePlanId == planId }
        return offer?.pricingPhases?.pricingPhaseList?.firstOrNull()?.formattedPrice
    }
}
