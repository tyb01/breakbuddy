package com.pawsup.android.ui.screens.onboarding;

import android.os.Build;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.CardDefaults;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import com.pawsup.android.R;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0004\u001a,\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a \u0010\u0007\u001a\u00020\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u001a,\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00032\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0010\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0011H\u0003\u001a,\u0010\u0012\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00032\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0016\u0010\u0013\u001a\u00020\u00012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u00a8\u0006\u0015"}, d2 = {"FinishPage", "", "notifOk", "", "onOpenNotifSettings", "Lkotlin/Function0;", "onStart", "OnboardingFlow", "onRequestBatteryExemption", "viewModel", "Lcom/pawsup/android/ui/screens/onboarding/OnboardingViewModel;", "OverlayPermissionPage", "granted", "onOpenSettings", "onContinue", "StepHeader", "stepIndex", "", "UsagePermissionPage", "WelcomePage", "onNext", "app_debug"})
public final class OnboardingScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void OnboardingFlow(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRequestBatteryExemption, @org.jetbrains.annotations.NotNull()
    com.pawsup.android.ui.screens.onboarding.OnboardingViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StepHeader(int stepIndex) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void WelcomePage(kotlin.jvm.functions.Function0<kotlin.Unit> onNext) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void UsagePermissionPage(boolean granted, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenSettings, kotlin.jvm.functions.Function0<kotlin.Unit> onContinue) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void OverlayPermissionPage(boolean granted, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenSettings, kotlin.jvm.functions.Function0<kotlin.Unit> onContinue) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FinishPage(boolean notifOk, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenNotifSettings, kotlin.jvm.functions.Function0<kotlin.Unit> onStart) {
    }
}