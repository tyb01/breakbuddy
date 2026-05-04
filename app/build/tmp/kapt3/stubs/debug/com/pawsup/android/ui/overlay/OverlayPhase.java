package com.pawsup.android.ui.overlay;

import android.net.Uri;
import android.view.Gravity;
import android.view.Surface;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.MutableState;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontWeight;
import android.graphics.Color;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/pawsup/android/ui/overlay/OverlayPhase;", "", "(Ljava/lang/String;I)V", "WalkIn", "Idle", "WalkOut", "app_debug"})
enum OverlayPhase {
    /*public static final*/ WalkIn /* = new WalkIn() */,
    /*public static final*/ Idle /* = new Idle() */,
    /*public static final*/ WalkOut /* = new WalkOut() */;
    
    OverlayPhase() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.pawsup.android.ui.overlay.OverlayPhase> getEntries() {
        return null;
    }
}