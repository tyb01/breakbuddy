package com.pawsup.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import androidx.activity.ComponentActivity;
import androidx.compose.animation.*;
import androidx.compose.foundation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.lazy.grid.GridCells;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import com.pawsup.R;
import com.pawsup.apppicker.AppPickerActivity;
import com.pawsup.cats.Cat;
import com.pawsup.cats.CatRegistry;
import com.pawsup.monitoring.OemAutostart;
import com.pawsup.paywall.PaywallActivity;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u0012\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\b\u0010\u0015\u001a\u00020\u0012H\u0014R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0016"}, d2 = {"Lcom/pawsup/settings/SettingsActivity;", "Landroidx/activity/ComponentActivity;", "()V", "catRegistry", "Lcom/pawsup/cats/CatRegistry;", "getCatRegistry", "()Lcom/pawsup/cats/CatRegistry;", "setCatRegistry", "(Lcom/pawsup/cats/CatRegistry;)V", "vm", "Lcom/pawsup/settings/SettingsViewModel;", "getVm", "()Lcom/pawsup/settings/SettingsViewModel;", "vm$delegate", "Lkotlin/Lazy;", "isIgnoringBatteryOptimizations", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "app_debug"})
public final class SettingsActivity extends androidx.activity.ComponentActivity {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy vm$delegate = null;
    @javax.inject.Inject()
    public com.pawsup.cats.CatRegistry catRegistry;
    
    public SettingsActivity() {
        super(0);
    }
    
    private final com.pawsup.settings.SettingsViewModel getVm() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.cats.CatRegistry getCatRegistry() {
        return null;
    }
    
    public final void setCatRegistry(@org.jetbrains.annotations.NotNull()
    com.pawsup.cats.CatRegistry p0) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    private final boolean isIgnoringBatteryOptimizations() {
        return false;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
}