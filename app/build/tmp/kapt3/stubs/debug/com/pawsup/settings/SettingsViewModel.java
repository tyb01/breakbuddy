package com.pawsup.settings;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import androidx.lifecycle.ViewModel;
import com.pawsup.billing.BillingRepository;
import com.pawsup.billing.Entitlements;
import com.pawsup.cats.Cat;
import com.pawsup.cats.CatRegistry;
import com.pawsup.data.UserPreferences;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B)\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0001\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0013H\u0002J\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u00132\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\u001f\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001cR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006 "}, d2 = {"Lcom/pawsup/settings/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "prefs", "Lcom/pawsup/data/UserPreferences;", "catRegistry", "Lcom/pawsup/cats/CatRegistry;", "billingRepo", "Lcom/pawsup/billing/BillingRepository;", "context", "Landroid/content/Context;", "(Lcom/pawsup/data/UserPreferences;Lcom/pawsup/cats/CatRegistry;Lcom/pawsup/billing/BillingRepository;Landroid/content/Context;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/pawsup/settings/SettingsUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "hasNotifPermission", "", "hasUsagePermission", "refreshPermissions", "", "setActiveCat", "catId", "", "setBreakDuration", "minutes", "", "setMonitorMe", "enabled", "setVisitLimit", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.data.UserPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.cats.CatRegistry catRegistry = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.billing.BillingRepository billingRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pawsup.settings.SettingsUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pawsup.settings.SettingsUiState> state = null;
    
    @javax.inject.Inject()
    public SettingsViewModel(@org.jetbrains.annotations.NotNull()
    com.pawsup.data.UserPreferences prefs, @org.jetbrains.annotations.NotNull()
    com.pawsup.cats.CatRegistry catRegistry, @org.jetbrains.annotations.NotNull()
    com.pawsup.billing.BillingRepository billingRepo, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pawsup.settings.SettingsUiState> getState() {
        return null;
    }
    
    public final void setVisitLimit(int minutes) {
    }
    
    public final void setBreakDuration(int minutes) {
    }
    
    public final void setActiveCat(@org.jetbrains.annotations.NotNull()
    java.lang.String catId) {
    }
    
    public final void setMonitorMe(boolean enabled, @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void refreshPermissions() {
    }
    
    private final boolean hasUsagePermission() {
        return false;
    }
    
    private final boolean hasNotifPermission() {
        return false;
    }
}