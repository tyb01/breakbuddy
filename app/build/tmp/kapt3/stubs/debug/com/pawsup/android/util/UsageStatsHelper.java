package com.pawsup.android.util;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001a\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u0004\u0018\u00010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u000f"}, d2 = {"Lcom/pawsup/android/util/UsageStatsHelper;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "usageStatsManager", "Landroid/app/usage/UsageStatsManager;", "getUsageStatsManager", "()Landroid/app/usage/UsageStatsManager;", "fallbackForegroundFromStats", "", "usm", "end", "", "foregroundPackageName", "app_debug"})
public final class UsageStatsHelper {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    @javax.inject.Inject()
    public UsageStatsHelper(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final android.app.usage.UsageStatsManager getUsageStatsManager() {
        return null;
    }
    
    /**
     * Resolves which app is most likely in the foreground. Uses a long usage-events window so a
     * session that stays in one app (no new events for a while) still resolves to that app.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String foregroundPackageName() {
        return null;
    }
    
    private final java.lang.String fallbackForegroundFromStats(android.app.usage.UsageStatsManager usm, long end) {
        return null;
    }
}