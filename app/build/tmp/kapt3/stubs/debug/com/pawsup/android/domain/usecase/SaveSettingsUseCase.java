package com.pawsup.android.domain.usecase;

import com.pawsup.android.data.datastore.UserAppOverrides;
import com.pawsup.android.data.repository.SettingsRepository;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/pawsup/android/domain/usecase/SaveSettingsUseCase;", "", "settingsRepository", "Lcom/pawsup/android/data/repository/SettingsRepository;", "(Lcom/pawsup/android/data/repository/SettingsRepository;)V", "setAppOverride", "", "pkg", "", "override", "Lcom/pawsup/android/data/datastore/UserAppOverrides;", "(Ljava/lang/String;Lcom/pawsup/android/data/datastore/UserAppOverrides;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateLimits", "usageLimitMin", "", "breakMin", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class SaveSettingsUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.android.data.repository.SettingsRepository settingsRepository = null;
    
    @javax.inject.Inject()
    public SaveSettingsUseCase(@org.jetbrains.annotations.NotNull()
    com.pawsup.android.data.repository.SettingsRepository settingsRepository) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateLimits(int usageLimitMin, int breakMin, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setAppOverride(@org.jetbrains.annotations.NotNull()
    java.lang.String pkg, @org.jetbrains.annotations.NotNull()
    com.pawsup.android.data.datastore.UserAppOverrides override, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}