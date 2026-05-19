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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000T\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0003\u001aN\u0010\b\u001a\u00020\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u00102\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u0010H\u0003\u001a6\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0003\u001a\\\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\u0010H\u0003\u001aB\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020\f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a6\u0010&\u001a\u00020\u00012\u0006\u0010\'\u001a\u00020\f2\u0006\u0010(\u001a\u00020\f2\u0006\u0010)\u001a\u00020\u00142\u0006\u0010*\u001a\u00020\f2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0010\u0010,\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0018H\u0003\u001a&\u0010-\u001a\u00020\u00012\u0006\u0010\'\u001a\u00020\f2\u0006\u0010.\u001a\u00020\f2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0018\u0010/\u001a\u00020\u00012\u0006\u0010\'\u001a\u00020\f2\u0006\u0010)\u001a\u00020\u0014H\u0003\u00a8\u00060"}, d2 = {"ActiveCatCard", "", "cat", "Lcom/pawsup/cats/Cat;", "onClick", "Lkotlin/Function0;", "context", "Landroid/content/Context;", "CafeTab", "cats", "", "activeCatId", "", "entitlements", "Lcom/pawsup/billing/Entitlements;", "onSelect", "Lkotlin/Function1;", "onAdopt", "CatGridCard", "isActive", "", "isOwned", "HomeTab", "state", "Lcom/pawsup/settings/SettingsUiState;", "onCompanionTap", "onEditApps", "onEditVisitLimit", "onEditBreakTime", "onMonitorMeToggle", "NumberPickerDialog", "title", "current", "", "range", "Lkotlin/ranges/IntRange;", "onConfirm", "onDismiss", "ReliabilityActionRow", "label", "description", "ok", "actionLabel", "onAction", "ReliabilityTab", "SettingsRow", "value", "StatusRow", "app_debug"})
public final class SettingsActivityKt {
    
    @androidx.compose.runtime.Composable()
    private static final void HomeTab(com.pawsup.settings.SettingsUiState state, kotlin.jvm.functions.Function0<kotlin.Unit> onCompanionTap, kotlin.jvm.functions.Function0<kotlin.Unit> onEditApps, kotlin.jvm.functions.Function0<kotlin.Unit> onEditVisitLimit, kotlin.jvm.functions.Function0<kotlin.Unit> onEditBreakTime, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onMonitorMeToggle) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CafeTab(java.util.List<com.pawsup.cats.Cat> cats, java.lang.String activeCatId, com.pawsup.billing.Entitlements entitlements, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSelect, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onAdopt) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ReliabilityTab(com.pawsup.settings.SettingsUiState state) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ActiveCatCard(com.pawsup.cats.Cat cat, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, android.content.Context context) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CatGridCard(com.pawsup.cats.Cat cat, boolean isActive, boolean isOwned, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, android.content.Context context) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SettingsRow(java.lang.String label, java.lang.String value, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatusRow(java.lang.String label, boolean ok) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ReliabilityActionRow(java.lang.String label, java.lang.String description, boolean ok, java.lang.String actionLabel, kotlin.jvm.functions.Function0<kotlin.Unit> onAction) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void NumberPickerDialog(java.lang.String title, int current, kotlin.ranges.IntRange range, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onConfirm, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
}