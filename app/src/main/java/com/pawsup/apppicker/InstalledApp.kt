package com.pawsup.apppicker

import android.graphics.drawable.Drawable

data class InstalledApp(
    val packageName: String,
    val displayName: String,
    val icon: Drawable
)
