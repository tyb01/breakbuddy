package com.pawsup.android.data.datastore;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import com.pawsup.android.data.model.AppSetting;
import com.pawsup.android.data.model.DefaultTrackedApps;
import javax.inject.Inject;
import javax.inject.Singleton;
import kotlinx.coroutines.flow.Flow;
import org.json.JSONObject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0017\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0006H\u00c6\u0003J\u0015\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\rH\u00c6\u0003JQ\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t2\b\b\u0002\u0010\f\u001a\u00020\rH\u00c6\u0001J\u0013\u0010 \u001a\u00020\u00032\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020\u0006H\u00d6\u0001J\t\u0010#\u001a\u00020\nH\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0010\u00a8\u0006$"}, d2 = {"Lcom/pawsup/android/data/datastore/UserPreferences;", "", "onboardingComplete", "", "serviceEnabled", "usageLimitMinutes", "", "breakDurationMinutes", "overrides", "", "", "Lcom/pawsup/android/data/datastore/UserAppOverrides;", "themeMode", "Lcom/pawsup/android/data/datastore/ThemeMode;", "(ZZIILjava/util/Map;Lcom/pawsup/android/data/datastore/ThemeMode;)V", "getBreakDurationMinutes", "()I", "getOnboardingComplete", "()Z", "getOverrides", "()Ljava/util/Map;", "getServiceEnabled", "getThemeMode", "()Lcom/pawsup/android/data/datastore/ThemeMode;", "getUsageLimitMinutes", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class UserPreferences {
    private final boolean onboardingComplete = false;
    private final boolean serviceEnabled = false;
    
    /**
     * Minutes of continuous in-app time before a break (extension “usage limit”).
     */
    private final int usageLimitMinutes = 0;
    private final int breakDurationMinutes = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, com.pawsup.android.data.datastore.UserAppOverrides> overrides = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.android.data.datastore.ThemeMode themeMode = null;
    
    public UserPreferences(boolean onboardingComplete, boolean serviceEnabled, int usageLimitMinutes, int breakDurationMinutes, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, com.pawsup.android.data.datastore.UserAppOverrides> overrides, @org.jetbrains.annotations.NotNull()
    com.pawsup.android.data.datastore.ThemeMode themeMode) {
        super();
    }
    
    public final boolean getOnboardingComplete() {
        return false;
    }
    
    public final boolean getServiceEnabled() {
        return false;
    }
    
    /**
     * Minutes of continuous in-app time before a break (extension “usage limit”).
     */
    public final int getUsageLimitMinutes() {
        return 0;
    }
    
    public final int getBreakDurationMinutes() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, com.pawsup.android.data.datastore.UserAppOverrides> getOverrides() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.android.data.datastore.ThemeMode getThemeMode() {
        return null;
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final boolean component2() {
        return false;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, com.pawsup.android.data.datastore.UserAppOverrides> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.android.data.datastore.ThemeMode component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.android.data.datastore.UserPreferences copy(boolean onboardingComplete, boolean serviceEnabled, int usageLimitMinutes, int breakDurationMinutes, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, com.pawsup.android.data.datastore.UserAppOverrides> overrides, @org.jetbrains.annotations.NotNull()
    com.pawsup.android.data.datastore.ThemeMode themeMode) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}