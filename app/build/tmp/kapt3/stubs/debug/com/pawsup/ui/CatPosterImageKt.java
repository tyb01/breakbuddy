package com.pawsup.ui;

import android.graphics.BitmapFactory;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ContentScale;
import com.pawsup.cats.Cat;
import com.pawsup.cats.CatAssetResolver;
import kotlinx.coroutines.Dispatchers;
import java.util.concurrent.ConcurrentHashMap;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001a*\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u001a \u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u000eH\u0002\u00a8\u0006\u0014"}, d2 = {"CatPosterImage", "", "cat", "Lcom/pawsup/cats/Cat;", "modifier", "Landroidx/compose/ui/Modifier;", "contentScale", "Landroidx/compose/ui/layout/ContentScale;", "chromaKeyBitmap", "Landroid/graphics/Bitmap;", "source", "keyColorArgb", "", "threshold", "", "softness", "smoothstep", "edge0", "edge1", "x", "app_debug"})
public final class CatPosterImageKt {
    
    /**
     * Displays a cat's poster with transparent background.
     * If the poster is already in [CatPosterCache] (preloaded at startup) it is
     * shown immediately with zero delay. Only falls back to background decoding
     * on a cache miss (e.g. very first launch before preload completes).
     */
    @androidx.compose.runtime.Composable()
    public static final void CatPosterImage(@org.jetbrains.annotations.NotNull()
    com.pawsup.cats.Cat cat, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.layout.ContentScale contentScale) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final android.graphics.Bitmap chromaKeyBitmap(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap source, int keyColorArgb, float threshold, float softness) {
        return null;
    }
    
    private static final float smoothstep(float edge0, float edge1, float x) {
        return 0.0F;
    }
}