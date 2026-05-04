package com.pawsup.android.data.model

import androidx.annotation.DrawableRes

data class AppSetting(
    val packageName: String,
    val displayName: String,
    @DrawableRes val iconRes: Int,
    val enabled: Boolean,
)
