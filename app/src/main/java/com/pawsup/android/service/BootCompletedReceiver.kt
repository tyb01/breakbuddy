package com.pawsup.android.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.pawsup.android.data.repository.SettingsRepository
import com.pawsup.android.util.PermissionHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BootCompletedReceiver : BroadcastReceiver() {

    @Inject
    lateinit var settingsRepository: SettingsRepository

    @Inject
    lateinit var permissionHelper: PermissionHelper

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action != Intent.ACTION_BOOT_COMPLETED) return
        val pending = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                ensureMonitoringServiceRunning(
                    context.applicationContext,
                    settingsRepository,
                    permissionHelper,
                )
            } finally {
                pending.finish()
            }
        }
    }
}
