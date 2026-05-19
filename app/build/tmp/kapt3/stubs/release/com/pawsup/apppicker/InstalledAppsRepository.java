package com.pawsup.apppicker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.LruCache;
import kotlinx.coroutines.Dispatchers;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0086@\u00a2\u0006\u0002\u0010\u000fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/pawsup/apppicker/InstalledAppsRepository;", "", "pm", "Landroid/content/pm/PackageManager;", "(Landroid/content/pm/PackageManager;)V", "cacheTime", "", "cachedApps", "", "Lcom/pawsup/apppicker/InstalledApp;", "iconCache", "Landroid/util/LruCache;", "", "Landroid/graphics/drawable/Drawable;", "getInstalledApps", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
public final class InstalledAppsRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.pm.PackageManager pm = null;
    @org.jetbrains.annotations.Nullable()
    private java.util.List<com.pawsup.apppicker.InstalledApp> cachedApps;
    private long cacheTime = 0L;
    @org.jetbrains.annotations.NotNull()
    private final android.util.LruCache<java.lang.String, android.graphics.drawable.Drawable> iconCache = null;
    
    @javax.inject.Inject()
    public InstalledAppsRepository(@org.jetbrains.annotations.NotNull()
    android.content.pm.PackageManager pm) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getInstalledApps(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.pawsup.apppicker.InstalledApp>> $completion) {
        return null;
    }
}