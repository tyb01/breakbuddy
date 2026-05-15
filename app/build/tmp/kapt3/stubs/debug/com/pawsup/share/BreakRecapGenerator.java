package com.pawsup.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import androidx.core.content.FileProvider;
import com.pawsup.cats.Cat;
import com.pawsup.cats.CatAssetResolver;
import java.io.File;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nJ(\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002\u00a8\u0006\u000e"}, d2 = {"Lcom/pawsup/share/BreakRecapGenerator;", "", "()V", "createShareIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "cat", "Lcom/pawsup/cats/Cat;", "durationMinutes", "", "petCount", "renderShareCard", "Landroid/graphics/Bitmap;", "app_debug"})
public final class BreakRecapGenerator {
    
    @javax.inject.Inject()
    public BreakRecapGenerator() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Intent createShareIntent(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.pawsup.cats.Cat cat, int durationMinutes, int petCount) {
        return null;
    }
    
    private final android.graphics.Bitmap renderShareCard(android.content.Context context, com.pawsup.cats.Cat cat, int durationMinutes, int petCount) {
        return null;
    }
}