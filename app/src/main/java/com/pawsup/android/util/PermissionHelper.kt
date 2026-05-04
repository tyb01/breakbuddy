package com.pawsup.android.util

import android.app.Activity
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import android.Manifest
import android.content.pm.PackageManager as PkgMgr

@Singleton
class PermissionHelper @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    fun hasUsageStatsPermission(): Boolean {
        val usm = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val end = System.currentTimeMillis()
        val stats = usm.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            end - 1000 * 60,
            end,
        )
        return stats != null && stats.isNotEmpty()
    }

    fun hasOverlayPermission(): Boolean = Settings.canDrawOverlays(context)

    fun hasPostNotificationsPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PkgMgr.PERMISSION_GRANTED
    }

    fun openUsageAccessSettings() {
        context.startActivity(
            Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }

    fun openOverlaySettings() {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:${context.packageName}"),
        )
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun requestNotifications(activity: Activity, requestCode: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            requestCode,
        )
    }

    fun isIgnoringBatteryOptimizations(): Boolean {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return pm.isIgnoringBatteryOptimizations(context.packageName)
    }

    fun requestIgnoreBatteryOptimizations() {
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
            data = Uri.parse("package:${context.packageName}")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

    fun getOemAutoStartIntent(): Intent? {
        val helperClass = runCatching {
            Class.forName("com.judemanutd.autostarter.AutoStartPermissionHelper")
        }.getOrNull() ?: return null
        val instance = runCatching {
            helperClass.getMethod("getInstance").invoke(null)
        }.getOrNull() ?: return null
        val method = helperClass.methods.firstOrNull {
            it.name == "getAutoStartPermissionIntent" || it.name == "getAutoStartIntent"
        } ?: return null
        return runCatching { method.invoke(instance, context) as? Intent }.getOrNull()
    }

    fun hasOemAutoStartSettings(): Boolean = getOemAutoStartIntent() != null

    fun openOemAutoStartSettings() {
        val intent = getOemAutoStartIntent() ?: return
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun isAggressiveOem(): Boolean {
        val o = Build.MANUFACTURER.lowercase()
        return o in listOf("xiaomi", "oppo", "vivo", "huawei", "oneplus", "realme", "samsung")
    }
}
