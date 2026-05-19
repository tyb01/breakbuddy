package com.pawsup.monitoring;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.pawsup.data.UserPreferences;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.Dispatchers;
import javax.inject.Inject;

/**
 * Fires on the chain-scheduled alarm. Checks Monitor Me, restarts the service
 * if it's dead, then reschedules the next alarm. Handles both the keep-alive alarm
 * and device boot / package-replaced broadcasts.
 */
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u0015"}, d2 = {"Lcom/pawsup/monitoring/KeepAliveReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "prefs", "Lcom/pawsup/data/UserPreferences;", "getPrefs", "()Lcom/pawsup/data/UserPreferences;", "setPrefs", "(Lcom/pawsup/data/UserPreferences;)V", "repo", "Lcom/pawsup/monitoring/MonitoringRepository;", "getRepo", "()Lcom/pawsup/monitoring/MonitoringRepository;", "setRepo", "(Lcom/pawsup/monitoring/MonitoringRepository;)V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_debug"})
public final class KeepAliveReceiver extends android.content.BroadcastReceiver {
    @javax.inject.Inject()
    public com.pawsup.data.UserPreferences prefs;
    @javax.inject.Inject()
    public com.pawsup.monitoring.MonitoringRepository repo;
    
    public KeepAliveReceiver() {
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
    public void onReceive(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
    }
}