package com.pawsup.android.service;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import androidx.compose.ui.platform.ComposeView;
import com.pawsup.android.ui.overlay.BreakCharacterAssets;
import com.pawsup.android.ui.overlay.MochiCharacterAssets;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\u0006\u0010\u0015\u001a\u00020\u0014J\b\u0010\u0016\u001a\u00020\u0014H\u0002J\u0006\u0010\u0017\u001a\u00020\u0018J\b\u0010\u0019\u001a\u00020\u0014H\u0002J\u0016\u0010\u001a\u001a\u00020\u00142\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00140\u001cH\u0002J.\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\b\b\u0002\u0010\"\u001a\u00020#2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00140\u001cR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/pawsup/android/service/OverlayManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "audioFocusListener", "Landroid/media/AudioManager$OnAudioFocusChangeListener;", "audioFocusRequest", "Landroid/media/AudioFocusRequest;", "layoutParams", "Landroid/view/WindowManager$LayoutParams;", "mainHandler", "Landroid/os/Handler;", "overlayLifecycle", "Lcom/pawsup/android/service/OverlayLifecycleOwner;", "overlayView", "Landroidx/compose/ui/platform/ComposeView;", "windowManager", "Landroid/view/WindowManager;", "abandonBreakAudioFocus", "", "dismissOverlay", "dismissOverlaySync", "isShowing", "", "requestBreakAudioFocus", "runOnMain", "block", "Lkotlin/Function0;", "showOverlay", "breakDurationSeconds", "", "catName", "", "character", "Lcom/pawsup/android/ui/overlay/BreakCharacterAssets;", "onBreakEnd", "app_debug"})
public final class OverlayManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final android.os.Handler mainHandler = null;
    @org.jetbrains.annotations.Nullable()
    private android.view.WindowManager windowManager;
    @org.jetbrains.annotations.Nullable()
    private androidx.compose.ui.platform.ComposeView overlayView;
    @org.jetbrains.annotations.Nullable()
    private android.view.WindowManager.LayoutParams layoutParams;
    @org.jetbrains.annotations.Nullable()
    private com.pawsup.android.service.OverlayLifecycleOwner overlayLifecycle;
    @org.jetbrains.annotations.Nullable()
    private android.media.AudioFocusRequest audioFocusRequest;
    @org.jetbrains.annotations.NotNull()
    private final android.media.AudioManager.OnAudioFocusChangeListener audioFocusListener = null;
    
    @javax.inject.Inject()
    public OverlayManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * WindowManager and Compose must run on the main thread. The monitor service uses a background
     * coroutine; without this marshalling, [ showOverlay ] crashes the app when the break starts.
     */
    private final void runOnMain(kotlin.jvm.functions.Function0<kotlin.Unit> block) {
    }
    
    public final void showOverlay(int breakDurationSeconds, @org.jetbrains.annotations.NotNull()
    java.lang.String catName, @org.jetbrains.annotations.NotNull()
    com.pawsup.android.ui.overlay.BreakCharacterAssets character, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBreakEnd) {
    }
    
    private final void requestBreakAudioFocus() {
    }
    
    private final void abandonBreakAudioFocus() {
    }
    
    public final void dismissOverlay() {
    }
    
    /**
     * Must run on the main thread (caller [runOnMain] or [showOverlay] internal path).
     */
    private final void dismissOverlaySync() {
    }
    
    public final boolean isShowing() {
        return false;
    }
}