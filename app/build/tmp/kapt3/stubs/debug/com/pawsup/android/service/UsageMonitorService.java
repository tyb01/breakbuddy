package com.pawsup.android.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.pawsup.android.R;
import com.pawsup.android.data.datastore.UserPreferences;
import com.pawsup.android.data.repository.SettingsRepository;
import com.pawsup.android.data.repository.UsageRepository;
import com.pawsup.android.domain.usecase.CheckUsageLimitUseCase;
import com.pawsup.android.ui.MainActivity;
import com.pawsup.android.util.PermissionHelper;
import com.pawsup.android.util.UsageStatsHelper;
import com.pawsup.android.worker.UsageWatchdogScheduler;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;
import kotlinx.coroutines.Dispatchers;

/**
 * Foreground monitor: usage accrues only for user-selected apps. Session timers live in memory
 * ([UsageRepository]); preferences use on-device DataStore. No analytics, accounts, or server upload.
 *
 * Break overlay applies while the user stays in the app that triggered the limit. Switching away
 * (home, recents, or another app) ends the break immediately so the overlay does not block the rest
 * of the device.
 */
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u0000 O2\u00020\u0001:\u0001OB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u00104\u001a\u0002052\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u00020\bH\u0002J\u0018\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020\b2\u0006\u0010<\u001a\u00020=H\u0002J$\u0010>\u001a\u00020:2\u0006\u00106\u001a\u0002072\b\u0010?\u001a\u0004\u0018\u00010\b2\b\u0010@\u001a\u0004\u0018\u00010\bH\u0002J\b\u0010A\u001a\u000205H\u0002J\u0010\u0010B\u001a\u0002052\u0006\u0010C\u001a\u00020\u0004H\u0002J\u0014\u0010D\u001a\u0004\u0018\u00010E2\b\u0010F\u001a\u0004\u0018\u00010GH\u0016J\b\u0010H\u001a\u000205H\u0016J\b\u0010I\u001a\u000205H\u0016J\"\u0010J\u001a\u00020=2\b\u0010F\u001a\u0004\u0018\u00010G2\u0006\u0010K\u001a\u00020=2\u0006\u0010L\u001a\u00020=H\u0016J\b\u0010M\u001a\u000205H\u0002J(\u0010N\u001a\u0002052\u0006\u00106\u001a\u0002072\n\b\u0002\u0010?\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010@\u001a\u0004\u0018\u00010\bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\u00020\u00108\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001e\u0010\u0015\u001a\u00020\u00168\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\"\u001a\u00020#8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010\'R\u001e\u0010(\u001a\u00020)8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u001e\u0010.\u001a\u00020/8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103\u00a8\u0006P"}, d2 = {"Lcom/pawsup/android/service/UsageMonitorService;", "Landroid/app/Service;", "()V", "breakActive", "", "breakLock", "", "breakTriggerPackage", "", "checkUsageLimitUseCase", "Lcom/pawsup/android/domain/usecase/CheckUsageLimitUseCase;", "getCheckUsageLimitUseCase", "()Lcom/pawsup/android/domain/usecase/CheckUsageLimitUseCase;", "setCheckUsageLimitUseCase", "(Lcom/pawsup/android/domain/usecase/CheckUsageLimitUseCase;)V", "overlayManager", "Lcom/pawsup/android/service/OverlayManager;", "getOverlayManager", "()Lcom/pawsup/android/service/OverlayManager;", "setOverlayManager", "(Lcom/pawsup/android/service/OverlayManager;)V", "permissionHelper", "Lcom/pawsup/android/util/PermissionHelper;", "getPermissionHelper", "()Lcom/pawsup/android/util/PermissionHelper;", "setPermissionHelper", "(Lcom/pawsup/android/util/PermissionHelper;)V", "pollJob", "Lkotlinx/coroutines/Job;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "screenOn", "screenReceiver", "Landroid/content/BroadcastReceiver;", "settingsRepository", "Lcom/pawsup/android/data/repository/SettingsRepository;", "getSettingsRepository", "()Lcom/pawsup/android/data/repository/SettingsRepository;", "setSettingsRepository", "(Lcom/pawsup/android/data/repository/SettingsRepository;)V", "usageRepository", "Lcom/pawsup/android/data/repository/UsageRepository;", "getUsageRepository", "()Lcom/pawsup/android/data/repository/UsageRepository;", "setUsageRepository", "(Lcom/pawsup/android/data/repository/UsageRepository;)V", "usageStatsHelper", "Lcom/pawsup/android/util/UsageStatsHelper;", "getUsageStatsHelper", "()Lcom/pawsup/android/util/UsageStatsHelper;", "setUsageStatsHelper", "(Lcom/pawsup/android/util/UsageStatsHelper;)V", "beginBreak", "", "prefs", "Lcom/pawsup/android/data/datastore/UserPreferences;", "packageName", "buildBreakNotification", "Landroid/app/Notification;", "catName", "seconds", "", "buildMonitoringNotification", "fg", "fgLabel", "createChannel", "finishBreak", "overlayDismissAlreadyCalled", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "flags", "startId", "startPolling", "updateNotification", "Companion", "app_debug"})
public final class UsageMonitorService extends android.app.Service {
    @javax.inject.Inject()
    public com.pawsup.android.util.UsageStatsHelper usageStatsHelper;
    @javax.inject.Inject()
    public com.pawsup.android.data.repository.SettingsRepository settingsRepository;
    @javax.inject.Inject()
    public com.pawsup.android.data.repository.UsageRepository usageRepository;
    @javax.inject.Inject()
    public com.pawsup.android.domain.usecase.CheckUsageLimitUseCase checkUsageLimitUseCase;
    @javax.inject.Inject()
    public com.pawsup.android.service.OverlayManager overlayManager;
    @javax.inject.Inject()
    public com.pawsup.android.util.PermissionHelper permissionHelper;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job pollJob;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Object breakLock = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String breakTriggerPackage;
    @kotlin.jvm.Volatile()
    private volatile boolean screenOn = true;
    @kotlin.jvm.Volatile()
    private volatile boolean breakActive = false;
    @org.jetbrains.annotations.NotNull()
    private final android.content.BroadcastReceiver screenReceiver = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_PAUSE = "com.pawsup.android.PAUSE_MONITORING";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_MAIN = "pawsup_main";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_BREAK = "pawsup_break";
    private static final int NOTIFICATION_ID = 1001;
    private static final int BREAK_NOTIFICATION_ID = 1002;
    public static final long POLL_MS = 1000L;
    @kotlin.jvm.Volatile()
    private static volatile boolean isRunning = false;
    @org.jetbrains.annotations.NotNull()
    public static final com.pawsup.android.service.UsageMonitorService.Companion Companion = null;
    
    public UsageMonitorService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.android.util.UsageStatsHelper getUsageStatsHelper() {
        return null;
    }
    
    public final void setUsageStatsHelper(@org.jetbrains.annotations.NotNull()
    com.pawsup.android.util.UsageStatsHelper p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.android.data.repository.SettingsRepository getSettingsRepository() {
        return null;
    }
    
    public final void setSettingsRepository(@org.jetbrains.annotations.NotNull()
    com.pawsup.android.data.repository.SettingsRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.android.data.repository.UsageRepository getUsageRepository() {
        return null;
    }
    
    public final void setUsageRepository(@org.jetbrains.annotations.NotNull()
    com.pawsup.android.data.repository.UsageRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.android.domain.usecase.CheckUsageLimitUseCase getCheckUsageLimitUseCase() {
        return null;
    }
    
    public final void setCheckUsageLimitUseCase(@org.jetbrains.annotations.NotNull()
    com.pawsup.android.domain.usecase.CheckUsageLimitUseCase p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.android.service.OverlayManager getOverlayManager() {
        return null;
    }
    
    public final void setOverlayManager(@org.jetbrains.annotations.NotNull()
    com.pawsup.android.service.OverlayManager p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.android.util.PermissionHelper getPermissionHelper() {
        return null;
    }
    
    public final void setPermissionHelper(@org.jetbrains.annotations.NotNull()
    com.pawsup.android.util.PermissionHelper p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
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
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    private final void startPolling() {
    }
    
    private final void finishBreak(boolean overlayDismissAlreadyCalled) {
    }
    
    private final void beginBreak(com.pawsup.android.data.datastore.UserPreferences prefs, java.lang.String packageName) {
    }
    
    private final void createChannel() {
    }
    
    private final void updateNotification(com.pawsup.android.data.datastore.UserPreferences prefs, java.lang.String fg, java.lang.String fgLabel) {
    }
    
    private final android.app.Notification buildMonitoringNotification(com.pawsup.android.data.datastore.UserPreferences prefs, java.lang.String fg, java.lang.String fgLabel) {
        return null;
    }
    
    private final android.app.Notification buildBreakNotification(java.lang.String catName, int seconds) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0080T\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0015"}, d2 = {"Lcom/pawsup/android/service/UsageMonitorService$Companion;", "", "()V", "ACTION_PAUSE", "", "BREAK_NOTIFICATION_ID", "", "CHANNEL_BREAK", "CHANNEL_MAIN", "NOTIFICATION_ID", "POLL_MS", "", "isRunning", "", "()Z", "setRunning", "(Z)V", "start", "", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final boolean isRunning() {
            return false;
        }
        
        public final void setRunning(boolean p0) {
        }
        
        public final void start(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
    }
}