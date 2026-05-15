package com.pawsup.data

import androidx.datastore.preferences.core.*

object DataStoreKeys {
    val MONITORED_PACKAGES               = stringSetPreferencesKey("monitored_packages")
    val MAX_MINUTES_PER_VISIT            = intPreferencesKey("max_minutes_per_visit")
    val BREAK_DURATION_MINUTES           = intPreferencesKey("break_duration_minutes")
    val ACTIVE_CAT_ID                    = stringPreferencesKey("active_cat_id")
    val OWNED_CAT_IDS                    = stringSetPreferencesKey("owned_cat_ids")
    val HAS_CAFE_BUNDLE                  = booleanPreferencesKey("has_cafe_bundle")
    val ENTITLEMENTS_LAST_SYNCED         = longPreferencesKey("entitlements_last_synced")
    val TOTAL_BREAKS_COMPLETED           = intPreferencesKey("total_breaks_completed")
    val TOTAL_PETS_LIFETIME              = longPreferencesKey("total_pets_lifetime")
    val ONBOARDING_COMPLETED             = booleanPreferencesKey("onboarding_completed")
    val USE_GL_CHROMA_KEY                = booleanPreferencesKey("use_gl_chroma_key")
    val USAGE_PERMISSION_GRANTED         = booleanPreferencesKey("usage_permission_granted")
    val OVERLAY_PERMISSION_GRANTED       = booleanPreferencesKey("overlay_permission_granted")
    val NOTIFICATIONS_PERMISSION_GRANTED = booleanPreferencesKey("notifications_permission_granted")

    val MONITOR_ME_ENABLED               = booleanPreferencesKey("monitor_me_enabled")

    fun guestVisitLastShown(catId: String) = longPreferencesKey("guest_visit_last_shown_$catId")
}
