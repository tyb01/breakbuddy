package com.pawsup.ui;

import android.graphics.BitmapFactory;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ContentScale;
import com.pawsup.cats.Cat;
import com.pawsup.cats.CatAssetResolver;
import kotlinx.coroutines.Dispatchers;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Application-scoped in-memory cache of chroma-keyed cat posters.
 * Populated at app startup by PawsUpApplication; composables read from here
 * synchronously, so the image is available the moment the composable first
 * enters composition — no LaunchedEffect delay.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u0005J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0006R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/pawsup/ui/CatPosterCache;", "", "()V", "store", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Landroidx/compose/ui/graphics/ImageBitmap;", "get", "catId", "put", "", "bmp", "app_release"})
public final class CatPosterCache {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.concurrent.ConcurrentHashMap<java.lang.String, androidx.compose.ui.graphics.ImageBitmap> store = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.pawsup.ui.CatPosterCache INSTANCE = null;
    
    private CatPosterCache() {
        super();
    }
    
    public final void put(@org.jetbrains.annotations.NotNull()
    java.lang.String catId, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.ImageBitmap bmp) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final androidx.compose.ui.graphics.ImageBitmap get(@org.jetbrains.annotations.NotNull()
    java.lang.String catId) {
        return null;
    }
}