package com.pawsup.android.data.repository;

import android.content.Context;
import android.content.pm.PackageManager;
import com.pawsup.android.data.datastore.SettingsDataStore;
import com.pawsup.android.data.datastore.ThemeMode;
import com.pawsup.android.data.datastore.UserAppOverrides;
import com.pawsup.android.data.datastore.UserPreferences;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;
import kotlinx.coroutines.flow.Flow;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0002J\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0086@\u00a2\u0006\u0002\u0010\u0015J\u000e\u0010\u0016\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u0010\u0015J\u000e\u0010\u0018\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u0010\u0015J\u0016\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\u001bJ\u0016\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u001eH\u0086@\u00a2\u0006\u0002\u0010\u001fJ\u000e\u0010 \u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u0015J\u001e\u0010!\u001a\u00020\u00172\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020#H\u0086@\u00a2\u0006\u0002\u0010$J\u001e\u0010%\u001a\u00020\u00172\u0006\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020\'H\u0086@\u00a2\u0006\u0002\u0010)R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lcom/pawsup/android/data/repository/SettingsRepository;", "", "context", "Landroid/content/Context;", "settingsDataStore", "Lcom/pawsup/android/data/datastore/SettingsDataStore;", "(Landroid/content/Context;Lcom/pawsup/android/data/datastore/SettingsDataStore;)V", "oemBatteryPrompted", "Lkotlinx/coroutines/flow/Flow;", "", "getOemBatteryPrompted", "()Lkotlinx/coroutines/flow/Flow;", "preferences", "Lcom/pawsup/android/data/datastore/UserPreferences;", "getPreferences", "labelForPackage", "", "pkg", "listAppSettings", "", "Lcom/pawsup/android/data/model/AppSetting;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setOemBatteryPrompted", "", "setOnboardingComplete", "setServiceEnabled", "enabled", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setThemeMode", "mode", "Lcom/pawsup/android/data/datastore/ThemeMode;", "(Lcom/pawsup/android/data/datastore/ThemeMode;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "snapshotPrefs", "updateAppOverride", "override", "Lcom/pawsup/android/data/datastore/UserAppOverrides;", "(Ljava/lang/String;Lcom/pawsup/android/data/datastore/UserAppOverrides;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateLimits", "usageLimitMin", "", "breakMin", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class SettingsRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.android.data.datastore.SettingsDataStore settingsDataStore = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<com.pawsup.android.data.datastore.UserPreferences> preferences = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> oemBatteryPrompted = null;
    
    @javax.inject.Inject()
    public SettingsRepository(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.pawsup.android.data.datastore.SettingsDataStore settingsDataStore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.pawsup.android.data.datastore.UserPreferences> getPreferences() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object snapshotPrefs(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pawsup.android.data.datastore.UserPreferences> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setOnboardingComplete(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setServiceEnabled(boolean enabled, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateLimits(int usageLimitMin, int breakMin, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateAppOverride(@org.jetbrains.annotations.NotNull()
    java.lang.String pkg, @org.jetbrains.annotations.NotNull()
    com.pawsup.android.data.datastore.UserAppOverrides override, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getOemBatteryPrompted() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setOemBatteryPrompted(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setThemeMode(@org.jetbrains.annotations.NotNull()
    com.pawsup.android.data.datastore.ThemeMode mode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object listAppSettings(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.pawsup.android.data.model.AppSetting>> $completion) {
        return null;
    }
    
    private final java.lang.String labelForPackage(java.lang.String pkg) {
        return null;
    }
}