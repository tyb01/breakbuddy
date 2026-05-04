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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a.\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\fH\u0007\u001a<\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\fH\u0003\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"WALK_OUT_MS", "", "WEBM_SLIDE_IN_MS", "", "CatOverlayContent", "", "breakDurationSeconds", "catName", "", "character", "Lcom/pawsup/android/ui/overlay/BreakCharacterAssets;", "onBreakEnd", "Lkotlin/Function0;", "WebmOverlayVideoSlot", "introRes", "idleRes", "phase", "Lcom/pawsup/android/ui/overlay/OverlayPhase;", "onIntroFinished", "onExitAnimationFinished", "app_debug"})
public final class CatOverlayViewKt {
    private static final long WALK_OUT_MS = 2000L;
    private static final int WEBM_SLIDE_IN_MS = 3000;
    
    @androidx.compose.runtime.Composable()
    public static final void CatOverlayContent(int breakDurationSeconds, @org.jetbrains.annotations.NotNull()
    java.lang.String catName, @org.jetbrains.annotations.NotNull()
    com.pawsup.android.ui.overlay.BreakCharacterAssets character, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBreakEnd) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void WebmOverlayVideoSlot(int introRes, int idleRes, com.pawsup.android.ui.overlay.OverlayPhase phase, kotlin.jvm.functions.Function0<kotlin.Unit> onIntroFinished, kotlin.jvm.functions.Function0<kotlin.Unit> onExitAnimationFinished) {
    }
}