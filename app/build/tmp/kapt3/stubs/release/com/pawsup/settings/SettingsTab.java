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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r\u00a8\u0006\u000e"}, d2 = {"Lcom/pawsup/settings/SettingsTab;", "", "label", "", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "(Ljava/lang/String;ILjava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;)V", "getIcon", "()Landroidx/compose/ui/graphics/vector/ImageVector;", "getLabel", "()Ljava/lang/String;", "HOME", "CAFE", "RELIABILITY", "app_release"})
enum SettingsTab {
    /*public static final*/ HOME /* = new HOME(null, null) */,
    /*public static final*/ CAFE /* = new CAFE(null, null) */,
    /*public static final*/ RELIABILITY /* = new RELIABILITY(null, null) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String label = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.ui.graphics.vector.ImageVector icon = null;
    
    SettingsTab(java.lang.String label, androidx.compose.ui.graphics.vector.ImageVector icon) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getIcon() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.pawsup.settings.SettingsTab> getEntries() {
        return null;
    }
}