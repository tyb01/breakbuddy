package com.pawsup.apppicker;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import com.pawsup.R;
import dagger.hilt.android.AndroidEntryPoint;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000<\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aF\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u001a&\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0003\u001a\u001a\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u0003\u00a8\u0006\u0014"}, d2 = {"AppPickerScreen", "", "state", "Lcom/pawsup/apppicker/AppPickerUiState;", "onSearch", "Lkotlin/Function1;", "", "onToggle", "onDone", "Lkotlin/Function0;", "AppRow", "app", "Lcom/pawsup/apppicker/InstalledApp;", "monitored", "", "DrawableImage", "drawable", "Landroid/graphics/drawable/Drawable;", "modifier", "Landroidx/compose/ui/Modifier;", "app_debug"})
public final class AppPickerActivityKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void AppPickerScreen(@org.jetbrains.annotations.NotNull()
    com.pawsup.apppicker.AppPickerUiState state, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSearch, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onToggle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDone) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AppRow(com.pawsup.apppicker.InstalledApp app, boolean monitored, kotlin.jvm.functions.Function0<kotlin.Unit> onToggle) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DrawableImage(android.graphics.drawable.Drawable drawable, androidx.compose.ui.Modifier modifier) {
    }
}