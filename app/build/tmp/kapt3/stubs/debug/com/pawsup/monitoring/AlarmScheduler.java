package com.pawsup.monitoring;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Schedules a chain-repeating exact alarm as a secondary keep-alive layer.
 * Fires even while the device is in Doze mode (unlike WorkManager periodic work
 * which can be deferred by hours on idle devices).
 *
 * Strategy:
 * - API < 31: setExactAndAllowWhileIdle — always available, fires in Doze
 * - API 31+ with exact alarm permission: setExactAndAllowWhileIdle
 * - API 31+ without exact alarm permission: setAndAllowWhileIdle (inexact but Doze-immune)
 *
 * No SCHEDULE_EXACT_ALARM permission required — falls back gracefully.
 * Each alarm fires KeepAliveReceiver, which reschedules the next one (chain pattern).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\t\u001a\u00020\nH\u0002J\u000e\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/pawsup/monitoring/AlarmScheduler;", "", "()V", "INTERVAL_MS", "", "REQUEST_CODE", "", "cancel", "", "context", "Landroid/content/Context;", "pendingIntent", "Landroid/app/PendingIntent;", "schedule", "app_debug"})
public final class AlarmScheduler {
    private static final long INTERVAL_MS = 900000L;
    private static final int REQUEST_CODE = 19265;
    @org.jetbrains.annotations.NotNull()
    public static final com.pawsup.monitoring.AlarmScheduler INSTANCE = null;
    
    private AlarmScheduler() {
        super();
    }
    
    public final void schedule(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void cancel(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    private final android.app.PendingIntent pendingIntent(android.content.Context context) {
        return null;
    }
}