package com.pawsup.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "pawsup_prefs")

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val store = context.dataStore

    val monitoredPackages: Flow<Set<String>> = store.data.map { it[DataStoreKeys.MONITORED_PACKAGES] ?: emptySet() }
    val maxMinutesPerVisit: Flow<Int>        = store.data.map { it[DataStoreKeys.MAX_MINUTES_PER_VISIT] ?: 15 }
    val breakDurationMinutes: Flow<Int>      = store.data.map { it[DataStoreKeys.BREAK_DURATION_MINUTES] ?: 2 }
    val activeCatId: Flow<String>            = store.data.map { it[DataStoreKeys.ACTIVE_CAT_ID] ?: "miso" }
    val ownedCatIds: Flow<Set<String>>       = store.data.map { it[DataStoreKeys.OWNED_CAT_IDS] ?: setOf("miso") }
    val hasCafeBundle: Flow<Boolean>         = store.data.map { it[DataStoreKeys.HAS_CAFE_BUNDLE] ?: false }
    val entitlementsLastSynced: Flow<Long>   = store.data.map { it[DataStoreKeys.ENTITLEMENTS_LAST_SYNCED] ?: 0L }
    val totalBreaksCompleted: Flow<Int>      = store.data.map { it[DataStoreKeys.TOTAL_BREAKS_COMPLETED] ?: 0 }
    val totalPetsLifetime: Flow<Long>        = store.data.map { it[DataStoreKeys.TOTAL_PETS_LIFETIME] ?: 0L }
    val onboardingCompleted: Flow<Boolean>   = store.data.map { it[DataStoreKeys.ONBOARDING_COMPLETED] ?: false }
    val useGlChromaKey: Flow<Boolean>        = store.data.map { it[DataStoreKeys.USE_GL_CHROMA_KEY] ?: false }
    val monitorMeEnabled: Flow<Boolean>      = store.data.map { it[DataStoreKeys.MONITOR_ME_ENABLED] ?: true }

    suspend fun setMonitoredPackages(packages: Set<String>)  = store.edit { it[DataStoreKeys.MONITORED_PACKAGES] = packages }
    suspend fun setMaxMinutesPerVisit(minutes: Int)          = store.edit { it[DataStoreKeys.MAX_MINUTES_PER_VISIT] = minutes }
    suspend fun setBreakDurationMinutes(minutes: Int)        = store.edit { it[DataStoreKeys.BREAK_DURATION_MINUTES] = minutes }
    suspend fun setActiveCatId(catId: String)                = store.edit { it[DataStoreKeys.ACTIVE_CAT_ID] = catId }
    suspend fun setOwnedCatIds(catIds: Set<String>)          = store.edit { it[DataStoreKeys.OWNED_CAT_IDS] = catIds }
    suspend fun setHasCafeBundle(has: Boolean)               = store.edit { it[DataStoreKeys.HAS_CAFE_BUNDLE] = has }
    suspend fun setEntitlementsLastSynced(ts: Long)          = store.edit { it[DataStoreKeys.ENTITLEMENTS_LAST_SYNCED] = ts }
    suspend fun setOnboardingCompleted(done: Boolean)        = store.edit { it[DataStoreKeys.ONBOARDING_COMPLETED] = done }
    suspend fun setUseGlChromaKey(use: Boolean)              = store.edit { it[DataStoreKeys.USE_GL_CHROMA_KEY] = use }
    suspend fun setMonitorMeEnabled(enabled: Boolean)        = store.edit { it[DataStoreKeys.MONITOR_ME_ENABLED] = enabled }
    suspend fun snapshotMonitorMeEnabled(): Boolean          = store.data.first()[DataStoreKeys.MONITOR_ME_ENABLED] ?: true

    suspend fun incrementBreaksCompleted() {
        val cur = store.data.first()[DataStoreKeys.TOTAL_BREAKS_COMPLETED] ?: 0
        store.edit { it[DataStoreKeys.TOTAL_BREAKS_COMPLETED] = cur + 1 }
    }

    suspend fun incrementPetsLifetime() {
        val cur = store.data.first()[DataStoreKeys.TOTAL_PETS_LIFETIME] ?: 0L
        store.edit { it[DataStoreKeys.TOTAL_PETS_LIFETIME] = cur + 1L }
    }

    suspend fun setGuestVisitLastShown(catId: String, ts: Long) =
        store.edit { it[DataStoreKeys.guestVisitLastShown(catId)] = ts }

    suspend fun getGuestVisitLastShown(catId: String): Long =
        store.data.first()[DataStoreKeys.guestVisitLastShown(catId)] ?: 0L

    // Synchronous snapshots for use inside services/workers
    suspend fun snapshotMonitoredPackages(): Set<String>  = store.data.first()[DataStoreKeys.MONITORED_PACKAGES] ?: emptySet()
    suspend fun snapshotMaxMinutes(): Int                  = store.data.first()[DataStoreKeys.MAX_MINUTES_PER_VISIT] ?: 15
    suspend fun snapshotBreakMinutes(): Int                = store.data.first()[DataStoreKeys.BREAK_DURATION_MINUTES] ?: 2
    suspend fun snapshotActiveCatId(): String              = store.data.first()[DataStoreKeys.ACTIVE_CAT_ID] ?: "miso"
    suspend fun snapshotTotalBreaks(): Int                 = store.data.first()[DataStoreKeys.TOTAL_BREAKS_COMPLETED] ?: 0
    suspend fun snapshotOwnedCatIds(): Set<String>         = store.data.first()[DataStoreKeys.OWNED_CAT_IDS] ?: setOf("miso")
    suspend fun snapshotHasCafeBundle(): Boolean           = store.data.first()[DataStoreKeys.HAS_CAFE_BUNDLE] ?: false
}
