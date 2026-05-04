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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B1\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aH\u0082@\u00a2\u0006\u0002\u0010\u001bJ\u0006\u0010\u001c\u001a\u00020\u001dJ\u0006\u0010\u001e\u001a\u00020\u001dJ\u0006\u0010\u001f\u001a\u00020\u001dJ\u0006\u0010 \u001a\u00020\u001dJ\u0016\u0010!\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#J\u0016\u0010%\u001a\u00020\u001d2\u0006\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020\u000fJ\u000e\u0010)\u001a\u00020\u001d2\u0006\u0010(\u001a\u00020\u000fR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0019\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lcom/pawsup/android/ui/screens/home/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "app", "Landroid/content/Context;", "settingsRepository", "Lcom/pawsup/android/data/repository/SettingsRepository;", "usageRepository", "Lcom/pawsup/android/data/repository/UsageRepository;", "saveSettings", "Lcom/pawsup/android/domain/usecase/SaveSettingsUseCase;", "permissionHelper", "Lcom/pawsup/android/util/PermissionHelper;", "(Landroid/content/Context;Lcom/pawsup/android/data/repository/SettingsRepository;Lcom/pawsup/android/data/repository/UsageRepository;Lcom/pawsup/android/domain/usecase/SaveSettingsUseCase;Lcom/pawsup/android/util/PermissionHelper;)V", "_showSaved", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_ui", "Lcom/pawsup/android/ui/screens/home/HomeUiState;", "showSaved", "Lkotlinx/coroutines/flow/StateFlow;", "getShowSaved", "()Lkotlinx/coroutines/flow/StateFlow;", "ui", "getUi", "buildState", "prefs", "Lcom/pawsup/android/data/datastore/UserPreferences;", "(Lcom/pawsup/android/data/datastore/UserPreferences;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "openOemAutoStartSettings", "", "openOverlaySettings", "openUsageAccessSettings", "requestBatteryExemption", "saveLimits", "usageLimitMin", "", "breakMin", "setAppEnabled", "pkg", "", "enabled", "setMonitoring", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context app = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.android.data.repository.SettingsRepository settingsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.android.data.repository.UsageRepository usageRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.android.domain.usecase.SaveSettingsUseCase saveSettings = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.android.util.PermissionHelper permissionHelper = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pawsup.android.ui.screens.home.HomeUiState> _ui = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pawsup.android.ui.screens.home.HomeUiState> ui = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _showSaved = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> showSaved = null;
    
    @javax.inject.Inject()
    public HomeViewModel(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context app, @org.jetbrains.annotations.NotNull()
    com.pawsup.android.data.repository.SettingsRepository settingsRepository, @org.jetbrains.annotations.NotNull()
    com.pawsup.android.data.repository.UsageRepository usageRepository, @org.jetbrains.annotations.NotNull()
    com.pawsup.android.domain.usecase.SaveSettingsUseCase saveSettings, @org.jetbrains.annotations.NotNull()
    com.pawsup.android.util.PermissionHelper permissionHelper) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pawsup.android.ui.screens.home.HomeUiState> getUi() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getShowSaved() {
        return null;
    }
    
    private final java.lang.Object buildState(com.pawsup.android.data.datastore.UserPreferences prefs, kotlin.coroutines.Continuation<? super com.pawsup.android.ui.screens.home.HomeUiState> $completion) {
        return null;
    }
    
    public final void setMonitoring(boolean enabled) {
    }
    
    public final void saveLimits(int usageLimitMin, int breakMin) {
    }
    
    public final void setAppEnabled(@org.jetbrains.annotations.NotNull()
    java.lang.String pkg, boolean enabled) {
    }
    
    public final void openUsageAccessSettings() {
    }
    
    public final void openOverlaySettings() {
    }
    
    public final void requestBatteryExemption() {
    }
    
    public final void openOemAutoStartSettings() {
    }
}