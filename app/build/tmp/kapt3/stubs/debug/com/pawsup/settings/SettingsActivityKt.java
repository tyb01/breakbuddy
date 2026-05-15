package com.pawsup.settings;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.compose.foundation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.lazy.grid.GridCells;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import com.pawsup.R;
import com.pawsup.apppicker.AppPickerActivity;
import com.pawsup.cats.Cat;
import com.pawsup.cats.CatAssetResolver;
import com.pawsup.cats.CatRegistry;
import com.pawsup.cats.CatTier;
import com.pawsup.paywall.PaywallActivity;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000T\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0003\u001a6\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0003\u001a\\\u0010\f\u001a\u00020\u00012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\u00142\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001aB\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00010\u00142\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u001e\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020 2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a&\u0010!\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u00102\u0006\u0010#\u001a\u00020\u00102\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001ax\u0010$\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020 2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u0014H\u0003\u001a\u0018\u0010,\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u00102\u0006\u0010-\u001a\u00020\nH\u0003\u00a8\u0006."}, d2 = {"ActiveCatCard", "", "cat", "Lcom/pawsup/cats/Cat;", "onClick", "Lkotlin/Function0;", "context", "Landroid/content/Context;", "CatGridCard", "isActive", "", "isOwned", "CatPickerDialog", "cats", "", "activeCatId", "", "entitlements", "Lcom/pawsup/billing/Entitlements;", "onSelect", "Lkotlin/Function1;", "onAdopt", "onDismiss", "NumberPickerDialog", "title", "current", "", "range", "Lkotlin/ranges/IntRange;", "onConfirm", "ReliabilityPanel", "state", "Lcom/pawsup/settings/SettingsUiState;", "SettingsRow", "label", "value", "SettingsScreen", "onCompanionTap", "onEditApps", "onEditVisitLimit", "onEditBreakTime", "onCafe", "onReliability", "onMonitorMeToggle", "StatusRow", "ok", "app_debug"})
public final class SettingsActivityKt {
    
    @androidx.compose.runtime.Composable()
    private static final void SettingsScreen(com.pawsup.settings.SettingsUiState state, kotlin.jvm.functions.Function0<kotlin.Unit> onCompanionTap, kotlin.jvm.functions.Function0<kotlin.Unit> onEditApps, kotlin.jvm.functions.Function0<kotlin.Unit> onEditVisitLimit, kotlin.jvm.functions.Function0<kotlin.Unit> onEditBreakTime, kotlin.jvm.functions.Function0<kotlin.Unit> onCafe, kotlin.jvm.functions.Function0<kotlin.Unit> onReliability, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onMonitorMeToggle) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ActiveCatCard(com.pawsup.cats.Cat cat, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, android.content.Context context) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SettingsRow(java.lang.String label, java.lang.String value, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CatPickerDialog(java.util.List<com.pawsup.cats.Cat> cats, java.lang.String activeCatId, com.pawsup.billing.Entitlements entitlements, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSelect, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onAdopt, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CatGridCard(com.pawsup.cats.Cat cat, boolean isActive, boolean isOwned, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, android.content.Context context) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ReliabilityPanel(com.pawsup.settings.SettingsUiState state, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatusRow(java.lang.String label, boolean ok) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void NumberPickerDialog(java.lang.String title, int current, kotlin.ranges.IntRange range, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onConfirm, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
}