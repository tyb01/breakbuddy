package com.pawsup.onboarding;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.activity.ComponentActivity;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.animation.*;
import androidx.compose.animation.core.*;
import androidx.compose.foundation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.core.content.ContextCompat;
import com.pawsup.R;
import com.pawsup.cats.CatRegistry;
import com.pawsup.cats.CatAssetResolver;
import com.pawsup.data.UserPreferences;
import com.pawsup.monitoring.MonitoringService;
import com.pawsup.settings.SettingsActivity;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\u001a(\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u0003\u001a\u0016\u0010\b\u001a\u00020\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0003\u001a\u0096\u0001\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\nH\u0003\u001a2\u0010\u0018\u001a\u00020\u00012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00070\nH\u0003\u001a.\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u00072\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0003\u001aj\u0010 \u001a\u00020\u00012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\nH\u0003\u001a2\u0010!\u001a\u00020\u00012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00070\nH\u0003\u001a\u001e\u0010\"\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0003\u00a8\u0006#"}, d2 = {"GuideStepCard", "", "number", "", "title", "subtitle", "active", "", "MeetMisoStep", "onFinish", "Lkotlin/Function0;", "OnboardingFlow", "step", "", "misoCat", "Lcom/pawsup/cats/Cat;", "onNext", "onPermissionsComplete", "onGrantUsageActual", "onGrantOverlay", "onGrantNotifications", "isUsageGranted", "isOverlayGranted", "isNotifGranted", "OverlayPermissionGuideScreen", "onOpenSettings", "onBack", "isPermissionGranted", "PermissionRow", "body", "granted", "onGrant", "PermissionsStep", "UsageAccessGuideScreen", "WelcomeStep", "app_debug"})
public final class OnboardingActivityKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.animation.ExperimentalAnimationApi.class})
    @androidx.compose.runtime.Composable()
    private static final void OnboardingFlow(int step, com.pawsup.cats.Cat misoCat, kotlin.jvm.functions.Function0<kotlin.Unit> onNext, kotlin.jvm.functions.Function0<kotlin.Unit> onPermissionsComplete, kotlin.jvm.functions.Function0<kotlin.Unit> onGrantUsageActual, kotlin.jvm.functions.Function0<kotlin.Unit> onGrantOverlay, kotlin.jvm.functions.Function0<kotlin.Unit> onGrantNotifications, kotlin.jvm.functions.Function0<kotlin.Unit> onFinish, kotlin.jvm.functions.Function0<java.lang.Boolean> isUsageGranted, kotlin.jvm.functions.Function0<java.lang.Boolean> isOverlayGranted, kotlin.jvm.functions.Function0<java.lang.Boolean> isNotifGranted) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void WelcomeStep(com.pawsup.cats.Cat misoCat, kotlin.jvm.functions.Function0<kotlin.Unit> onNext) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PermissionsStep(kotlin.jvm.functions.Function0<kotlin.Unit> onGrantUsageActual, kotlin.jvm.functions.Function0<kotlin.Unit> onGrantOverlay, kotlin.jvm.functions.Function0<kotlin.Unit> onGrantNotifications, kotlin.jvm.functions.Function0<kotlin.Unit> onPermissionsComplete, kotlin.jvm.functions.Function0<java.lang.Boolean> isUsageGranted, kotlin.jvm.functions.Function0<java.lang.Boolean> isOverlayGranted, kotlin.jvm.functions.Function0<java.lang.Boolean> isNotifGranted) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void UsageAccessGuideScreen(kotlin.jvm.functions.Function0<kotlin.Unit> onOpenSettings, kotlin.jvm.functions.Function0<kotlin.Unit> onBack, kotlin.jvm.functions.Function0<java.lang.Boolean> isPermissionGranted) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void OverlayPermissionGuideScreen(kotlin.jvm.functions.Function0<kotlin.Unit> onOpenSettings, kotlin.jvm.functions.Function0<kotlin.Unit> onBack, kotlin.jvm.functions.Function0<java.lang.Boolean> isPermissionGranted) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void GuideStepCard(java.lang.String number, java.lang.String title, java.lang.String subtitle, boolean active) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PermissionRow(java.lang.String title, java.lang.String body, boolean granted, kotlin.jvm.functions.Function0<kotlin.Unit> onGrant) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MeetMisoStep(kotlin.jvm.functions.Function0<kotlin.Unit> onFinish) {
    }
}