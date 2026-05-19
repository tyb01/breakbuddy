package com.pawsup.monitoring;

import android.app.*;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.pawsup.R;
import com.pawsup.break_experience.BreakOverlayActivity;
import com.pawsup.data.UserPreferences;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.*;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\b\u0007\u0018\u0000 +2\u00020\u0001:\u0001+B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\u000e\u0010\u0019\u001a\u00020\u001aH\u0082@\u00a2\u0006\u0002\u0010\u001bJ\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u001d\u001a\u00020\u0016H\u0002J\u0014\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\b\u0010\"\u001a\u00020\u001aH\u0016J\b\u0010#\u001a\u00020\u001aH\u0016J\u000e\u0010$\u001a\u00020\u001aH\u0082@\u00a2\u0006\u0002\u0010\u001bJ\"\u0010%\u001a\u00020&2\b\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\'\u001a\u00020&2\u0006\u0010(\u001a\u00020&H\u0016J\u0006\u0010)\u001a\u00020\u001aJ\b\u0010*\u001a\u00020\u001aH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u00020\b8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001e\u0010\r\u001a\u00020\u000e8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/pawsup/monitoring/MonitoringService;", "Landroid/app/Service;", "()V", "breakFireable", "", "currentSessionPackage", "", "prefs", "Lcom/pawsup/data/UserPreferences;", "getPrefs", "()Lcom/pawsup/data/UserPreferences;", "setPrefs", "(Lcom/pawsup/data/UserPreferences;)V", "repo", "Lcom/pawsup/monitoring/MonitoringRepository;", "getRepo", "()Lcom/pawsup/monitoring/MonitoringRepository;", "setRepo", "(Lcom/pawsup/monitoring/MonitoringRepository;)V", "scope", "Lkotlinx/coroutines/CoroutineScope;", "sessionStartTime", "", "buildNotification", "Landroid/app/Notification;", "fireBreakOverlay", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getForegroundPackage", "windowMs", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onPoll", "onStartCommand", "", "flags", "startId", "resetSession", "startPolling", "Companion", "app_release"})
public final class MonitoringService extends android.app.Service {
    @javax.inject.Inject()
    public com.pawsup.data.UserPreferences prefs;
    @javax.inject.Inject()
    public com.pawsup.monitoring.MonitoringRepository repo;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String currentSessionPackage;
    private long sessionStartTime = 0L;
    private boolean breakFireable = true;
    private static final int NOTIF_ID = 1001;
    @org.jetbrains.annotations.NotNull()
    public static final com.pawsup.monitoring.MonitoringService.Companion Companion = null;
    
    public MonitoringService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.data.UserPreferences getPrefs() {
        return null;
    }
    
    public final void setPrefs(@org.jetbrains.annotations.NotNull()
    com.pawsup.data.UserPreferences p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.monitoring.MonitoringRepository getRepo() {
        return null;
    }
    
    public final void setRepo(@org.jetbrains.annotations.NotNull()
    com.pawsup.monitoring.MonitoringRepository p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
        return null;
    }
    
    private final void startPolling() {
    }
    
    private final java.lang.Object onPoll(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object fireBreakOverlay(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Returns the package with the most recent ACTIVITY_RESUMED event.
     * [windowMs] is read from the configured visit limit (2× the limit, min 10 min)
     * so the event stays in-window for the entire session duration.
     */
    private final java.lang.String getForegroundPackage(long windowMs) {
        return null;
    }
    
    private final android.app.Notification buildNotification() {
        return null;
    }
    
    public final void resetSession() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/pawsup/monitoring/MonitoringService$Companion;", "", "()V", "NOTIF_ID", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}