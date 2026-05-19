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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u001f\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001Ba\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\u000fJ\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0005H\u00c6\u0003J\t\u0010 \u001a\u00020\u0005H\u00c6\u0003J\t\u0010!\u001a\u00020\tH\u00c6\u0003J\t\u0010\"\u001a\u00020\u000bH\u00c6\u0003J\t\u0010#\u001a\u00020\u000bH\u00c6\u0003J\t\u0010$\u001a\u00020\u000bH\u00c6\u0003J\t\u0010%\u001a\u00020\u000bH\u00c6\u0003Je\u0010&\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000b2\b\b\u0002\u0010\u000e\u001a\u00020\u000bH\u00c6\u0001J\u0013\u0010\'\u001a\u00020\u000b2\b\u0010(\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010)\u001a\u00020\u0005H\u00d6\u0001J\t\u0010*\u001a\u00020+H\u00d6\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u000e\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013R\u0011\u0010\r\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0013\u00a8\u0006,"}, d2 = {"Lcom/pawsup/settings/SettingsUiState;", "", "activeCat", "Lcom/pawsup/cats/Cat;", "visitLimitMin", "", "breakDurationMin", "monitoredCount", "entitlements", "Lcom/pawsup/billing/Entitlements;", "usagePermGranted", "", "overlayPermGranted", "notifPermGranted", "monitorMeEnabled", "(Lcom/pawsup/cats/Cat;IIILcom/pawsup/billing/Entitlements;ZZZZ)V", "getActiveCat", "()Lcom/pawsup/cats/Cat;", "getBreakDurationMin", "()I", "getEntitlements", "()Lcom/pawsup/billing/Entitlements;", "getMonitorMeEnabled", "()Z", "getMonitoredCount", "getNotifPermGranted", "getOverlayPermGranted", "getUsagePermGranted", "getVisitLimitMin", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "", "app_release"})
public final class SettingsUiState {
    @org.jetbrains.annotations.Nullable()
    private final com.pawsup.cats.Cat activeCat = null;
    private final int visitLimitMin = 0;
    private final int breakDurationMin = 0;
    private final int monitoredCount = 0;
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.billing.Entitlements entitlements = null;
    private final boolean usagePermGranted = false;
    private final boolean overlayPermGranted = false;
    private final boolean notifPermGranted = false;
    private final boolean monitorMeEnabled = false;
    
    public SettingsUiState(@org.jetbrains.annotations.Nullable()
    com.pawsup.cats.Cat activeCat, int visitLimitMin, int breakDurationMin, int monitoredCount, @org.jetbrains.annotations.NotNull()
    com.pawsup.billing.Entitlements entitlements, boolean usagePermGranted, boolean overlayPermGranted, boolean notifPermGranted, boolean monitorMeEnabled) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.pawsup.cats.Cat getActiveCat() {
        return null;
    }
    
    public final int getVisitLimitMin() {
        return 0;
    }
    
    public final int getBreakDurationMin() {
        return 0;
    }
    
    public final int getMonitoredCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.billing.Entitlements getEntitlements() {
        return null;
    }
    
    public final boolean getUsagePermGranted() {
        return false;
    }
    
    public final boolean getOverlayPermGranted() {
        return false;
    }
    
    public final boolean getNotifPermGranted() {
        return false;
    }
    
    public final boolean getMonitorMeEnabled() {
        return false;
    }
    
    public SettingsUiState() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.pawsup.cats.Cat component1() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.billing.Entitlements component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    public final boolean component8() {
        return false;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.settings.SettingsUiState copy(@org.jetbrains.annotations.Nullable()
    com.pawsup.cats.Cat activeCat, int visitLimitMin, int breakDurationMin, int monitoredCount, @org.jetbrains.annotations.NotNull()
    com.pawsup.billing.Entitlements entitlements, boolean usagePermGranted, boolean overlayPermGranted, boolean notifPermGranted, boolean monitorMeEnabled) {
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