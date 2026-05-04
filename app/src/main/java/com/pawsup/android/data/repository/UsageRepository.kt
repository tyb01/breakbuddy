package com.pawsup.android.data.repository

import javax.inject.Inject
import javax.inject.Singleton

/**
 * In-memory session timers: counts time only while a tracked app stays in the foreground.
 * Leaving for another app resets that session, except a hop into [exemptDestinationPackage]
 * (this app) so opening PawsUp settings does not wipe progress.
 *
 * When [foregroundPackage] is null (usage stats gap), the previous foreground package’s session is
 * cleared so a return to that app starts from zero and does not instantly hit the limit again.
 */
@Singleton
class UsageRepository @Inject constructor() {

    private val lock = Any()
    private val sessionMs = mutableMapOf<String, Long>()
    private var lastForegroundPackage: String? = null

    /**
     * @param exemptDestinationPackage typically [Context.getPackageName] — moving here does not clear
     * the previous app’s session.
     */
    fun syncForeground(foregroundPackage: String?, exemptDestinationPackage: String) {
        synchronized(lock) {
            if (foregroundPackage == null) {
                // Usage stats sometimes briefly return null after leaving an app. Clear the previous
                // foreground session so we do not immediately re-trigger a break on the next visit.
                lastForegroundPackage?.let { prev ->
                    sessionMs[prev] = 0L
                }
                lastForegroundPackage = null
                return
            }

            val prev = lastForegroundPackage
            if (prev != null && prev != foregroundPackage) {
                if (foregroundPackage != exemptDestinationPackage) {
                    sessionMs[prev] = 0L
                }
            }
            lastForegroundPackage = foregroundPackage
        }
    }

    /** Increment only when [packageName] is still the synced foreground package. */
    fun addSessionMs(packageName: String, deltaMs: Long) {
        synchronized(lock) {
            if (lastForegroundPackage != packageName) return
            if (deltaMs <= 0L) return
            sessionMs[packageName] = (sessionMs[packageName] ?: 0L) + deltaMs
        }
    }

    fun currentSessionMs(packageName: String): Long {
        synchronized(lock) {
            return sessionMs[packageName] ?: 0L
        }
    }

    fun resetSession(packageName: String) {
        synchronized(lock) {
            sessionMs[packageName] = 0L
        }
    }
}
