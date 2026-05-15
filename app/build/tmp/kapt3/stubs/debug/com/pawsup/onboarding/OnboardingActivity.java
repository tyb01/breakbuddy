package com.pawsup.onboarding;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.activity.ComponentActivity;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.animation.*;
import androidx.compose.foundation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.core.content.ContextCompat;
import com.pawsup.R;
import com.pawsup.apppicker.AppPickerActivity;
import com.pawsup.cats.CatAssetResolver;
import com.pawsup.data.UserPreferences;
import com.pawsup.monitoring.MonitoringService;
import com.pawsup.settings.SettingsActivity;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\u0012\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014R+\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048B@BX\u0082\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u000f\u001a\u00020\u00108\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006\u001b"}, d2 = {"Lcom/pawsup/onboarding/OnboardingActivity;", "Landroidx/activity/ComponentActivity;", "()V", "<set-?>", "", "currentStep", "getCurrentStep", "()I", "setCurrentStep", "(I)V", "currentStep$delegate", "Landroidx/compose/runtime/MutableIntState;", "notifPermissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "prefs", "Lcom/pawsup/data/UserPreferences;", "getPrefs", "()Lcom/pawsup/data/UserPreferences;", "setPrefs", "(Lcom/pawsup/data/UserPreferences;)V", "hasUsagePermission", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"})
public final class OnboardingActivity extends androidx.activity.ComponentActivity {
    @javax.inject.Inject()
    public com.pawsup.data.UserPreferences prefs;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableIntState currentStep$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> notifPermissionLauncher = null;
    
    public OnboardingActivity() {
        super(0);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.data.UserPreferences getPrefs() {
        return null;
    }
    
    public final void setPrefs(@org.jetbrains.annotations.NotNull()
    com.pawsup.data.UserPreferences p0) {
    }
    
    private final int getCurrentStep() {
        return 0;
    }
    
    private final void setCurrentStep(int p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final boolean hasUsagePermission() {
        return false;
    }
}