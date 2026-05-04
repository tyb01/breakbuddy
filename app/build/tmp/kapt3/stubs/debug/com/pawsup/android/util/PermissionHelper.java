package com.pawsup.android.util;

import android.app.Activity;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;
import android.Manifest;
import android.content.pm.PackageManager;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\bJ\u0006\u0010\n\u001a\u00020\bJ\u0006\u0010\u000b\u001a\u00020\bJ\u0006\u0010\f\u001a\u00020\bJ\u0006\u0010\r\u001a\u00020\bJ\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000fJ\u0006\u0010\u0011\u001a\u00020\u000fJ\u0006\u0010\u0012\u001a\u00020\u000fJ\u0016\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/pawsup/android/util/PermissionHelper;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getOemAutoStartIntent", "Landroid/content/Intent;", "hasOemAutoStartSettings", "", "hasOverlayPermission", "hasPostNotificationsPermission", "hasUsageStatsPermission", "isAggressiveOem", "isIgnoringBatteryOptimizations", "openOemAutoStartSettings", "", "openOverlaySettings", "openUsageAccessSettings", "requestIgnoreBatteryOptimizations", "requestNotifications", "activity", "Landroid/app/Activity;", "requestCode", "", "app_debug"})
public final class PermissionHelper {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    @javax.inject.Inject()
    public PermissionHelper(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final boolean hasUsageStatsPermission() {
        return false;
    }
    
    public final boolean hasOverlayPermission() {
        return false;
    }
    
    public final boolean hasPostNotificationsPermission() {
        return false;
    }
    
    public final void openUsageAccessSettings() {
    }
    
    public final void openOverlaySettings() {
    }
    
    public final void requestNotifications(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, int requestCode) {
    }
    
    public final boolean isIgnoringBatteryOptimizations() {
        return false;
    }
    
    public final void requestIgnoreBatteryOptimizations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.content.Intent getOemAutoStartIntent() {
        return null;
    }
    
    public final boolean hasOemAutoStartSettings() {
        return false;
    }
    
    public final void openOemAutoStartSettings() {
    }
    
    public final boolean isAggressiveOem() {
        return false;
    }
}