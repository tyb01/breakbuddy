package com.pawsup.android.data.model

import androidx.annotation.DrawableRes
import com.pawsup.android.R

data class TrackedApp(
    val packageName: String,
    val displayName: String,
    @DrawableRes val iconRes: Int,
)

object DefaultTrackedApps {
    val ALL: List<TrackedApp> = listOf(
        TrackedApp("com.instagram.android", "Instagram", R.drawable.ic_instagram),
        TrackedApp("com.zhiliaoapp.musically", "TikTok", R.drawable.ic_tiktok),
        TrackedApp("com.twitter.android", "X", R.drawable.ic_twitter),
        TrackedApp("com.google.android.youtube", "YouTube", R.drawable.ic_youtube),
        TrackedApp("com.facebook.katana", "Facebook", R.drawable.ic_facebook),
        TrackedApp("com.snapchat.android", "Snapchat", R.drawable.ic_snapchat),
        TrackedApp("com.reddit.frontpage", "Reddit", R.drawable.ic_reddit),
        TrackedApp("com.linkedin.android", "LinkedIn", R.drawable.ic_linkedin),
    )

    fun byPackage(pkg: String): TrackedApp? = ALL.find { it.packageName == pkg }
}
