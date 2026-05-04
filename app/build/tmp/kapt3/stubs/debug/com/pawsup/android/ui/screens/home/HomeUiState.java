package com.pawsup.android.ui.screens.home;

import android.content.Context;
import android.content.Intent;
import androidx.lifecycle.ViewModel;
import com.pawsup.android.data.datastore.UserAppOverrides;
import com.pawsup.android.data.datastore.UserPreferences;
import com.pawsup.android.data.repository.SettingsRepository;
import com.pawsup.android.data.repository.UsageRepository;
import com.pawsup.android.domain.usecase.SaveSettingsUseCase;
import com.pawsup.android.service.UsageMonitorService;
import com.pawsup.android.util.PermissionHelper;
import com.pawsup.android.worker.UsageWatchdogScheduler;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u001a\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\b\u0012\u0006\u0010\u000b\u001a\u00020\b\u0012\u0006\u0010\f\u001a\u00020\b\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001b\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001c\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\bH\u00c6\u0003JU\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\b2\b\b\u0002\u0010\f\u001a\u00020\bH\u00c6\u0001J\u0013\u0010 \u001a\u00020\b2\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020#H\u00d6\u0001J\t\u0010$\u001a\u00020%H\u00d6\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u000b\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\f\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\n\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011\u00a8\u0006&"}, d2 = {"Lcom/pawsup/android/ui/screens/home/HomeUiState;", "", "prefs", "Lcom/pawsup/android/data/datastore/UserPreferences;", "appRows", "", "Lcom/pawsup/android/ui/screens/home/TrackedAppRow;", "serviceRunning", "", "usageAccessOk", "overlayOk", "batteryOptimizationsIgnored", "oemAutoStartAvailable", "(Lcom/pawsup/android/data/datastore/UserPreferences;Ljava/util/List;ZZZZZ)V", "getAppRows", "()Ljava/util/List;", "getBatteryOptimizationsIgnored", "()Z", "getOemAutoStartAvailable", "getOverlayOk", "getPrefs", "()Lcom/pawsup/android/data/datastore/UserPreferences;", "getServiceRunning", "getUsageAccessOk", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "", "toString", "", "app_debug"})
public final class HomeUiState {
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.android.data.datastore.UserPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.pawsup.android.ui.screens.home.TrackedAppRow> appRows = null;
    private final boolean serviceRunning = false;
    private final boolean usageAccessOk = false;
    private final boolean overlayOk = false;
    private final boolean batteryOptimizationsIgnored = false;
    private final boolean oemAutoStartAvailable = false;
    
    public HomeUiState(@org.jetbrains.annotations.NotNull()
    com.pawsup.android.data.datastore.UserPreferences prefs, @org.jetbrains.annotations.NotNull()
    java.util.List<com.pawsup.android.ui.screens.home.TrackedAppRow> appRows, boolean serviceRunning, boolean usageAccessOk, boolean overlayOk, boolean batteryOptimizationsIgnored, boolean oemAutoStartAvailable) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.android.data.datastore.UserPreferences getPrefs() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.pawsup.android.ui.screens.home.TrackedAppRow> getAppRows() {
        return null;
    }
    
    public final boolean getServiceRunning() {
        return false;
    }
    
    public final boolean getUsageAccessOk() {
        return false;
    }
    
    public final boolean getOverlayOk() {
        return false;
    }
    
    public final boolean getBatteryOptimizationsIgnored() {
        return false;
    }
    
    public final boolean getOemAutoStartAvailable() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.android.data.datastore.UserPreferences component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.pawsup.android.ui.screens.home.TrackedAppRow> component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.android.ui.screens.home.HomeUiState copy(@org.jetbrains.annotations.NotNull()
    com.pawsup.android.data.datastore.UserPreferences prefs, @org.jetbrains.annotations.NotNull()
    java.util.List<com.pawsup.android.ui.screens.home.TrackedAppRow> appRows, boolean serviceRunning, boolean usageAccessOk, boolean overlayOk, boolean batteryOptimizationsIgnored, boolean oemAutoStartAvailable) {
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