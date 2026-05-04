package com.pawsup.android.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * In-memory session timers: counts time only while a tracked app stays in the foreground.
 * Leaving for another app resets that session, except a hop into [exemptDestinationPackage]
 * (this app) so opening PawsUp settings does not wipe progress.
 *
 * When [foregroundPackage] is null (usage stats gap), the previous foreground package’s session is
 * cleared so a return to that app starts from zero and does not instantly hit the limit again.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\bJ\u000e\u0010\r\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004J\u0018\u0010\u000f\u001a\u00020\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0011\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/pawsup/android/data/repository/UsageRepository;", "", "()V", "lastForegroundPackage", "", "lock", "sessionMs", "", "", "addSessionMs", "", "packageName", "deltaMs", "currentSessionMs", "resetSession", "syncForeground", "foregroundPackage", "exemptDestinationPackage", "app_debug"})
public final class UsageRepository {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Object lock = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Long> sessionMs = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String lastForegroundPackage;
    
    @javax.inject.Inject()
    public UsageRepository() {
        super();
    }
    
    /**
     * @param exemptDestinationPackage typically [Context.getPackageName] — moving here does not clear
     * the previous app’s session.
     */
    public final void syncForeground(@org.jetbrains.annotations.Nullable()
    java.lang.String foregroundPackage, @org.jetbrains.annotations.NotNull()
    java.lang.String exemptDestinationPackage) {
    }
    
    /**
     * Increment only when [packageName] is still the synced foreground package.
     */
    public final void addSessionMs(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, long deltaMs) {
    }
    
    public final long currentSessionMs(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName) {
        return 0L;
    }
    
    public final void resetSession(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName) {
    }
}