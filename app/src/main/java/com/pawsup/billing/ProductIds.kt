package com.pawsup.billing

object ProductIds {
    val PER_CAT = mapOf(
        "yuki"    to PerCatSub("pawsup_yuki",    "yuki-monthly",    "yuki-yearly"),
        "mocha"   to PerCatSub("pawsup_mocha",   "mocha-monthly",   "mocha-yearly"),
        "shadow"  to PerCatSub("pawsup_shadow",  "shadow-monthly",  "shadow-yearly"),
        "biscuit" to PerCatSub("pawsup_biscuit", "biscuit-monthly", "biscuit-yearly"),
    )
    const val CAFE_PRODUCT = "pawsup_cafe_bundle"
    const val CAFE_MONTHLY = "cafe-monthly"
    const val CAFE_YEARLY  = "cafe-yearly"
}

data class PerCatSub(val productId: String, val monthlyPlan: String, val yearlyPlan: String)
