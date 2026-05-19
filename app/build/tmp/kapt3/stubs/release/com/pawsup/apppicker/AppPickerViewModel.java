package com.pawsup.apppicker;

import androidx.lifecycle.ViewModel;
import com.pawsup.data.UserPreferences;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\u000e\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0012R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0015"}, d2 = {"Lcom/pawsup/apppicker/AppPickerViewModel;", "Landroidx/lifecycle/ViewModel;", "appsRepo", "Lcom/pawsup/apppicker/InstalledAppsRepository;", "prefs", "Lcom/pawsup/data/UserPreferences;", "(Lcom/pawsup/apppicker/InstalledAppsRepository;Lcom/pawsup/data/UserPreferences;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/pawsup/apppicker/AppPickerUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "loadApps", "", "setSearchQuery", "query", "", "toggleMonitored", "packageName", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AppPickerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.apppicker.InstalledAppsRepository appsRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.data.UserPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pawsup.apppicker.AppPickerUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pawsup.apppicker.AppPickerUiState> state = null;
    
    @javax.inject.Inject()
    public AppPickerViewModel(@org.jetbrains.annotations.NotNull()
    com.pawsup.apppicker.InstalledAppsRepository appsRepo, @org.jetbrains.annotations.NotNull()
    com.pawsup.data.UserPreferences prefs) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pawsup.apppicker.AppPickerUiState> getState() {
        return null;
    }
    
    private final void loadApps() {
    }
    
    public final void setSearchQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void toggleMonitored(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName) {
    }
}