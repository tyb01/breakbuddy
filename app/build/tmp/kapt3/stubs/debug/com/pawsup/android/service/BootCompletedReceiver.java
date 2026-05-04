package com.pawsup.android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.pawsup.android.data.repository.SettingsRepository;
import com.pawsup.android.util.PermissionHelper;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;
import kotlinx.coroutines.Dispatchers;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u0015"}, d2 = {"Lcom/pawsup/android/service/BootCompletedReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "permissionHelper", "Lcom/pawsup/android/util/PermissionHelper;", "getPermissionHelper", "()Lcom/pawsup/android/util/PermissionHelper;", "setPermissionHelper", "(Lcom/pawsup/android/util/PermissionHelper;)V", "settingsRepository", "Lcom/pawsup/android/data/repository/SettingsRepository;", "getSettingsRepository", "()Lcom/pawsup/android/data/repository/SettingsRepository;", "setSettingsRepository", "(Lcom/pawsup/android/data/repository/SettingsRepository;)V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_debug"})
public final class BootCompletedReceiver extends android.content.BroadcastReceiver {
    @javax.inject.Inject()
    public com.pawsup.android.data.repository.SettingsRepository settingsRepository;
    @javax.inject.Inject()
    public com.pawsup.android.util.PermissionHelper permissionHelper;
    
    public BootCompletedReceiver() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.android.data.repository.SettingsRepository getSettingsRepository() {
        return null;
    }
    
    public final void setSettingsRepository(@org.jetbrains.annotations.NotNull()
    com.pawsup.android.data.repository.SettingsRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.android.util.PermissionHelper getPermissionHelper() {
        return null;
    }
    
    public final void setPermissionHelper(@org.jetbrains.annotations.NotNull()
    com.pawsup.android.util.PermissionHelper p0) {
    }
    
    @java.lang.Override()
    public void onReceive(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
    }
}