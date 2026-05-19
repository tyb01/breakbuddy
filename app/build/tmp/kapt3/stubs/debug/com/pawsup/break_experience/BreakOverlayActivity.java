package com.pawsup.break_experience;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import androidx.activity.ComponentActivity;
import androidx.compose.animation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.core.view.WindowCompat;
import com.pawsup.cats.Cat;
import com.pawsup.cats.CatRegistry;
import com.pawsup.break_experience.components.*;
import com.pawsup.paywall.PaywallActivity;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0013H\u0014J\u0010\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001b\u001a\u00020\u0013H\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001d"}, d2 = {"Lcom/pawsup/break_experience/BreakOverlayActivity;", "Landroidx/activity/ComponentActivity;", "()V", "catRegistry", "Lcom/pawsup/cats/CatRegistry;", "getCatRegistry", "()Lcom/pawsup/cats/CatRegistry;", "setCatRegistry", "(Lcom/pawsup/cats/CatRegistry;)V", "greetingPlayer", "Landroid/media/MediaPlayer;", "purrPlayer", "vm", "Lcom/pawsup/break_experience/BreakOverlayViewModel;", "getVm", "()Lcom/pawsup/break_experience/BreakOverlayViewModel;", "vm$delegate", "Lkotlin/Lazy;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "playGreeting", "cat", "Lcom/pawsup/cats/Cat;", "playPurr", "prepareAudio", "Companion", "app_debug"})
public final class BreakOverlayActivity extends androidx.activity.ComponentActivity {
    @javax.inject.Inject()
    public com.pawsup.cats.CatRegistry catRegistry;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy vm$delegate = null;
    @org.jetbrains.annotations.Nullable()
    private android.media.MediaPlayer greetingPlayer;
    @org.jetbrains.annotations.Nullable()
    private android.media.MediaPlayer purrPlayer;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_CAT_ID = "cat_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_BREAK_MINUTES = "break_duration_minutes";
    @org.jetbrains.annotations.NotNull()
    public static final com.pawsup.break_experience.BreakOverlayActivity.Companion Companion = null;
    
    public BreakOverlayActivity() {
        super(0);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.cats.CatRegistry getCatRegistry() {
        return null;
    }
    
    public final void setCatRegistry(@org.jetbrains.annotations.NotNull()
    com.pawsup.cats.CatRegistry p0) {
    }
    
    private final com.pawsup.break_experience.BreakOverlayViewModel getVm() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    private final void prepareAudio() {
    }
    
    private final void playGreeting(com.pawsup.cats.Cat cat) {
    }
    
    private final void playPurr(com.pawsup.cats.Cat cat) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/pawsup/break_experience/BreakOverlayActivity$Companion;", "", "()V", "EXTRA_BREAK_MINUTES", "", "EXTRA_CAT_ID", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}