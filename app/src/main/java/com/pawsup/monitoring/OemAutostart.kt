package com.pawsup.monitoring

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build

/**
 * Provides deep-links to OEM-specific autostart / background-launch settings screens.
 *
 * Samsung, Xiaomi, Huawei, Oppo, Vivo, OnePlus, and Asus all have proprietary
 * battery managers that kill foreground services independently of Android's standard
 * battery optimization. The only reliable fix is to direct the user to their device's
 * specific screen and have them whitelist the app.
 *
 * Intent targets are sourced from device-specific testing and the open-source
 * AutoStarter library (MIT). Each entry is validated via resolveActivity before use.
 */
object OemAutostart {

    data class OemEntry(val label: String, val intent: Intent)

    private val candidates: List<OemEntry> = listOf(

        // ── Xiaomi / MIUI ──────────────────────────────────────────────────
        OemEntry("Xiaomi autostart", Intent("miui.intent.action.APP_PERM_EDITOR")
            .setClassName("com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity")),

        OemEntry("Xiaomi autostart (alt)", Intent()
            .setComponent(ComponentName("com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity"))),

        // ── Samsung ────────────────────────────────────────────────────────
        OemEntry("Samsung device care", Intent()
            .setComponent(ComponentName("com.samsung.android.lool",
                "com.samsung.android.sm.ui.battery.BatteryActivity"))),

        OemEntry("Samsung battery usage", Intent()
            .setComponent(ComponentName("com.samsung.android.sm.devicesecurity",
                "com.samsung.android.sm.ui.battery.BatteryActivity"))),

        // ── Huawei / EMUI ──────────────────────────────────────────────────
        OemEntry("Huawei protected apps", Intent("com.huawei.systemmanager.action.PROTECTED_APP")
            .setPackage("com.huawei.systemmanager")
            .putExtra("pkgname", "com.pawsup")),

        OemEntry("Huawei power manager", Intent()
            .setComponent(ComponentName("com.huawei.systemmanager",
                "com.huawei.systemmanager.optimize.process.ProtectActivity"))),

        // ── Oppo / ColorOS ─────────────────────────────────────────────────
        OemEntry("Oppo startup manager", Intent()
            .setComponent(ComponentName("com.coloros.safecenter",
                "com.coloros.safecenter.permission.startup.StartupAppListActivity"))),

        OemEntry("Oppo startup manager (alt)", Intent()
            .setComponent(ComponentName("com.oppo.safe",
                "com.oppo.safe.permission.startup.StartupAppListActivity"))),

        // ── Vivo / FuntouchOS ──────────────────────────────────────────────
        OemEntry("Vivo background app", Intent()
            .setComponent(ComponentName("com.vivo.permissionmanager",
                "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"))),

        OemEntry("Vivo background (alt)", Intent()
            .setComponent(ComponentName("com.iqoo.secure",
                "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"))),

        // ── OnePlus / OxygenOS ─────────────────────────────────────────────
        OemEntry("OnePlus auto-launch", Intent()
            .setComponent(ComponentName("com.oneplus.security",
                "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity"))),

        // ── Asus / ZenUI ───────────────────────────────────────────────────
        OemEntry("Asus autostart", Intent()
            .setComponent(ComponentName("com.asus.mobilemanager",
                "com.asus.mobilemanager.autostart.AutoStartActivity"))),

        OemEntry("Asus autostart (alt)", Intent()
            .setComponent(ComponentName("com.asus.mobilemanager",
                "com.asus.mobilemanager.entry.FunctionActivity"))
            .putExtra("com.asus.mobilemanager.entry.KEY_TITLE", "com.asus.mobilemanager.autostart")),
    )

    /** Returns the first resolvable OEM intent for this device, or null if none match. */
    fun getIntent(context: Context): Intent? =
        candidates.firstOrNull { canResolve(context, it.intent) }?.intent

    /** True if this device has a known OEM autostart screen we can open. */
    fun isAvailable(context: Context): Boolean = getIntent(context) != null

    /** Human-readable label for the entry, for display in the reliability panel. */
    fun getLabel(context: Context): String =
        candidates.firstOrNull { canResolve(context, it.intent) }?.label
            ?: "OEM autostart settings"

    private fun canResolve(context: Context, intent: Intent): Boolean =
        context.packageManager.resolveActivity(intent, 0) != null
}
