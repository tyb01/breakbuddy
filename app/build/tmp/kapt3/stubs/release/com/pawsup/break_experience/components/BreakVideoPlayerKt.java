package com.pawsup.break_experience.components;

import android.net.Uri;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import com.pawsup.break_experience.BreakState;
import com.pawsup.cats.CatAssetResolver;
import java.util.concurrent.atomic.AtomicBoolean;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a>\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u00a8\u0006\u000b"}, d2 = {"BreakVideoPlayer", "", "catId", "", "breakState", "Lcom/pawsup/break_experience/BreakState;", "onEntranceComplete", "Lkotlin/Function0;", "onOutroComplete", "modifier", "Landroidx/compose/ui/Modifier;", "app_release"})
public final class BreakVideoPlayerKt {
    
    /**
     * Single ExoPlayer manages the full break sequence as a 3-item playlist:
     *  [0] entrance.mp4  → plays once, then automatically transitions to idle
     *  [1] idle.mp4      → loops indefinitely via REPEAT_MODE_ONE
     *  [2] outro.mp4     → played on demand, no repeat
     *
     * All three items are prepared upfront so the idle starts instantly when
     * entrance ends, and the outro is already buffered when needed.
     */
    @androidx.compose.runtime.Composable()
    public static final void BreakVideoPlayer(@org.jetbrains.annotations.NotNull()
    java.lang.String catId, @org.jetbrains.annotations.NotNull()
    com.pawsup.break_experience.BreakState breakState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onEntranceComplete, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOutroComplete, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
}