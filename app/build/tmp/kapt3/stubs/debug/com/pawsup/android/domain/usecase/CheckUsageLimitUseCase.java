package com.pawsup.android.domain.usecase;

import com.pawsup.android.data.datastore.UserPreferences;
import com.pawsup.android.data.repository.SettingsRepository;
import com.pawsup.android.data.repository.UsageRepository;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0086@\u00a2\u0006\u0002\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/pawsup/android/domain/usecase/CheckUsageLimitUseCase;", "", "settingsRepository", "Lcom/pawsup/android/data/repository/SettingsRepository;", "usageRepository", "Lcom/pawsup/android/data/repository/UsageRepository;", "(Lcom/pawsup/android/data/repository/SettingsRepository;Lcom/pawsup/android/data/repository/UsageRepository;)V", "shouldEnforceBreak", "", "prefs", "Lcom/pawsup/android/data/datastore/UserPreferences;", "foregroundPackage", "", "(Lcom/pawsup/android/data/datastore/UserPreferences;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class CheckUsageLimitUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.android.data.repository.SettingsRepository settingsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.android.data.repository.UsageRepository usageRepository = null;
    
    @javax.inject.Inject()
    public CheckUsageLimitUseCase(@org.jetbrains.annotations.NotNull()
    com.pawsup.android.data.repository.SettingsRepository settingsRepository, @org.jetbrains.annotations.NotNull()
    com.pawsup.android.data.repository.UsageRepository usageRepository) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object shouldEnforceBreak(@org.jetbrains.annotations.NotNull()
    com.pawsup.android.data.datastore.UserPreferences prefs, @org.jetbrains.annotations.Nullable()
    java.lang.String foregroundPackage, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
}