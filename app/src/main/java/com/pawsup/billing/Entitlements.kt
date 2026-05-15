package com.pawsup.billing

data class Entitlements(
    val ownedCatIds: Set<String>,
    val hasCafeBundle: Boolean,
    val lastSyncedAt: Long
) {
    fun owns(catId: String) = catId == "miso" || hasCafeBundle || catId in ownedCatIds

    companion object {
        val DEFAULT = Entitlements(setOf("miso"), false, 0L)
        private const val OFFLINE_FALLBACK_DAYS = 14L
        private const val MS_PER_DAY = 86_400_000L

        fun offlineFallback(lastSyncedAt: Long): Entitlements? {
            val daysSinceSync = (System.currentTimeMillis() - lastSyncedAt) / MS_PER_DAY
            return if (daysSinceSync >= OFFLINE_FALLBACK_DAYS) DEFAULT else null
        }
    }
}
