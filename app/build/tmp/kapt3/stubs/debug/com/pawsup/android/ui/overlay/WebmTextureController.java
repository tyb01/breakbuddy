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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0002\u0018\u00002\u00020\u0001B[\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000e0\u0010\u00a2\u0006\u0002\u0010\u0012J \u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u0011H\u0016J\u0010\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J \u0010\u001f\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u0011H\u0016J\u0010\u0010 \u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0006\u0010!\u001a\u00020\u000eJ\u0010\u0010\"\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020\u0014H\u0002J\b\u0010$\u001a\u00020\u000eH\u0002J\b\u0010%\u001a\u00020\u000eH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000e0\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/pawsup/android/ui/overlay/WebmTextureController;", "Landroid/view/TextureView$SurfaceTextureListener;", "context", "Landroid/content/Context;", "textureView", "Landroid/view/TextureView;", "introUri", "Landroid/net/Uri;", "idleUri", "videoStage", "Landroidx/compose/runtime/MutableState;", "Lcom/pawsup/android/ui/overlay/WebmVideoStage;", "onIntroDone", "Lkotlin/Function0;", "", "onVideoSize", "Lkotlin/Function2;", "", "(Landroid/content/Context;Landroid/view/TextureView;Landroid/net/Uri;Landroid/net/Uri;Landroidx/compose/runtime/MutableState;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function2;)V", "player", "Landroid/media/MediaPlayer;", "released", "", "surface", "Landroid/view/Surface;", "onSurfaceTextureAvailable", "st", "Landroid/graphics/SurfaceTexture;", "width", "height", "onSurfaceTextureDestroyed", "onSurfaceTextureSizeChanged", "onSurfaceTextureUpdated", "release", "reportSize", "mp", "startIdle", "startIntro", "app_debug"})
final class WebmTextureController implements android.view.TextureView.SurfaceTextureListener {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final android.view.TextureView textureView = null;
    @org.jetbrains.annotations.NotNull()
    private final android.net.Uri introUri = null;
    @org.jetbrains.annotations.NotNull()
    private final android.net.Uri idleUri = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState<com.pawsup.android.ui.overlay.WebmVideoStage> videoStage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<kotlin.Unit> onIntroDone = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function2<java.lang.Integer, java.lang.Integer, kotlin.Unit> onVideoSize = null;
    @org.jetbrains.annotations.NotNull()
    private final android.media.MediaPlayer player = null;
    @org.jetbrains.annotations.Nullable()
    private android.view.Surface surface;
    private boolean released = false;
    
    public WebmTextureController(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.view.TextureView textureView, @org.jetbrains.annotations.NotNull()
    android.net.Uri introUri, @org.jetbrains.annotations.NotNull()
    android.net.Uri idleUri, @org.jetbrains.annotations.NotNull()
    androidx.compose.runtime.MutableState<com.pawsup.android.ui.overlay.WebmVideoStage> videoStage, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onIntroDone, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super java.lang.Integer, kotlin.Unit> onVideoSize) {
        super();
    }
    
    public final void release() {
    }
    
    private final void reportSize(android.media.MediaPlayer mp) {
    }
    
    private final void startIntro() {
    }
    
    private final void startIdle() {
    }
    
    @java.lang.Override()
    public void onSurfaceTextureAvailable(@org.jetbrains.annotations.NotNull()
    android.graphics.SurfaceTexture st, int width, int height) {
    }
    
    @java.lang.Override()
    public void onSurfaceTextureSizeChanged(@org.jetbrains.annotations.NotNull()
    android.graphics.SurfaceTexture st, int width, int height) {
    }
    
    @java.lang.Override()
    public boolean onSurfaceTextureDestroyed(@org.jetbrains.annotations.NotNull()
    android.graphics.SurfaceTexture st) {
        return false;
    }
    
    @java.lang.Override()
    public void onSurfaceTextureUpdated(@org.jetbrains.annotations.NotNull()
    android.graphics.SurfaceTexture st) {
    }
}