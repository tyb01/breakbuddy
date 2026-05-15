package com.pawsup.data;

import android.content.Context;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\"\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b#\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\'\u001a\u00020\u000e2\u0006\u0010(\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010)J\u000e\u0010*\u001a\u00020+H\u0086@\u00a2\u0006\u0002\u0010,J\u000e\u0010-\u001a\u00020+H\u0086@\u00a2\u0006\u0002\u0010,J\u0016\u0010.\u001a\u00020 2\u0006\u0010(\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010)J\u0016\u0010/\u001a\u00020 2\u0006\u00100\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u00101J\u0016\u00102\u001a\u00020 2\u0006\u00103\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u00104J\u001e\u00105\u001a\u00020 2\u0006\u0010(\u001a\u00020\u00072\u0006\u00103\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u00106J\u0016\u00107\u001a\u00020 2\u0006\u00108\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u00109J\u0016\u0010:\u001a\u00020 2\u0006\u00100\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u00101J\u0016\u0010;\u001a\u00020 2\u0006\u0010<\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u00109J\u001c\u0010=\u001a\u00020 2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00070\u0018H\u0086@\u00a2\u0006\u0002\u0010?J\u0016\u0010@\u001a\u00020 2\u0006\u0010A\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u00109J\u001c\u0010B\u001a\u00020 2\f\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00070\u0018H\u0086@\u00a2\u0006\u0002\u0010?J\u0016\u0010D\u001a\u00020 2\u0006\u0010E\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u00109J\u000e\u0010F\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010,J\u000e\u0010G\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010,J\u000e\u0010H\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010,J\u000e\u0010I\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010,J\u000e\u0010J\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010,J\u0014\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00070\u0018H\u0086@\u00a2\u0006\u0002\u0010,J\u0014\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00070\u0018H\u0086@\u00a2\u0006\u0002\u0010,J\u000e\u0010M\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010,R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\tR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\tR\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\tR\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00110\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\tR\u001d\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00180\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\tR\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00110\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\tR\u001d\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00180\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\tR\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\tR\u0017\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\tR\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00110\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\t\u00a8\u0006N"}, d2 = {"Lcom/pawsup/data/UserPreferences;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "activeCatId", "Lkotlinx/coroutines/flow/Flow;", "", "getActiveCatId", "()Lkotlinx/coroutines/flow/Flow;", "breakDurationMinutes", "", "getBreakDurationMinutes", "entitlementsLastSynced", "", "getEntitlementsLastSynced", "hasCafeBundle", "", "getHasCafeBundle", "maxMinutesPerVisit", "getMaxMinutesPerVisit", "monitorMeEnabled", "getMonitorMeEnabled", "monitoredPackages", "", "getMonitoredPackages", "onboardingCompleted", "getOnboardingCompleted", "ownedCatIds", "getOwnedCatIds", "store", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "totalBreaksCompleted", "getTotalBreaksCompleted", "totalPetsLifetime", "getTotalPetsLifetime", "useGlChromaKey", "getUseGlChromaKey", "getGuestVisitLastShown", "catId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "incrementBreaksCompleted", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "incrementPetsLifetime", "setActiveCatId", "setBreakDurationMinutes", "minutes", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setEntitlementsLastSynced", "ts", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setGuestVisitLastShown", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setHasCafeBundle", "has", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setMaxMinutesPerVisit", "setMonitorMeEnabled", "enabled", "setMonitoredPackages", "packages", "(Ljava/util/Set;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setOnboardingCompleted", "done", "setOwnedCatIds", "catIds", "setUseGlChromaKey", "use", "snapshotActiveCatId", "snapshotBreakMinutes", "snapshotHasCafeBundle", "snapshotMaxMinutes", "snapshotMonitorMeEnabled", "snapshotMonitoredPackages", "snapshotOwnedCatIds", "snapshotTotalBreaks", "app_debug"})
public final class UserPreferences {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> store = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.Set<java.lang.String>> monitoredPackages = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> maxMinutesPerVisit = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> breakDurationMinutes = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.String> activeCatId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.Set<java.lang.String>> ownedCatIds = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> hasCafeBundle = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Long> entitlementsLastSynced = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> totalBreaksCompleted = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Long> totalPetsLifetime = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> onboardingCompleted = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> useGlChromaKey = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> monitorMeEnabled = null;
    
    @javax.inject.Inject()
    public UserPreferences(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.Set<java.lang.String>> getMonitoredPackages() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getMaxMinutesPerVisit() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getBreakDurationMinutes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getActiveCatId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.Set<java.lang.String>> getOwnedCatIds() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getHasCafeBundle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Long> getEntitlementsLastSynced() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalBreaksCompleted() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Long> getTotalPetsLifetime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getOnboardingCompleted() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getUseGlChromaKey() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getMonitorMeEnabled() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setMonitoredPackages(@org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> packages, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.datastore.preferences.core.Preferences> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setMaxMinutesPerVisit(int minutes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.datastore.preferences.core.Preferences> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setBreakDurationMinutes(int minutes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.datastore.preferences.core.Preferences> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setActiveCatId(@org.jetbrains.annotations.NotNull()
    java.lang.String catId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.datastore.preferences.core.Preferences> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setOwnedCatIds(@org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> catIds, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.datastore.preferences.core.Preferences> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setHasCafeBundle(boolean has, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.datastore.preferences.core.Preferences> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setEntitlementsLastSynced(long ts, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.datastore.preferences.core.Preferences> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setOnboardingCompleted(boolean done, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.datastore.preferences.core.Preferences> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setUseGlChromaKey(boolean use, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.datastore.preferences.core.Preferences> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setMonitorMeEnabled(boolean enabled, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.datastore.preferences.core.Preferences> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object snapshotMonitorMeEnabled(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object incrementBreaksCompleted(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object incrementPetsLifetime(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setGuestVisitLastShown(@org.jetbrains.annotations.NotNull()
    java.lang.String catId, long ts, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.datastore.preferences.core.Preferences> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getGuestVisitLastShown(@org.jetbrains.annotations.NotNull()
    java.lang.String catId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object snapshotMonitoredPackages(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.Set<java.lang.String>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object snapshotMaxMinutes(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object snapshotBreakMinutes(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object snapshotActiveCatId(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object snapshotTotalBreaks(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object snapshotOwnedCatIds(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.Set<java.lang.String>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object snapshotHasCafeBundle(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
}