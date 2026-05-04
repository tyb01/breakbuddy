package com.pawsup.android.worker;

import android.content.Context;
import androidx.hilt.work.HiltWorker;
import androidx.work.CoroutineWorker;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.WorkManager;
import androidx.work.WorkerParameters;
import com.pawsup.android.data.repository.SettingsRepository;
import com.pawsup.android.util.PermissionHelper;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import java.util.concurrent.TimeUnit;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/pawsup/android/worker/UsageWatchdogScheduler;", "", "()V", "NAME", "", "schedule", "", "context", "Landroid/content/Context;", "app_debug"})
public final class UsageWatchdogScheduler {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String NAME = "pawsup_watchdog";
    @org.jetbrains.annotations.NotNull()
    public static final com.pawsup.android.worker.UsageWatchdogScheduler INSTANCE = null;
    
    private UsageWatchdogScheduler() {
        super();
    }
    
    public final void schedule(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
}