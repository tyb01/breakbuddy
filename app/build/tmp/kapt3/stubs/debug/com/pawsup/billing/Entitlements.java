package com.pawsup.billing;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u000e\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\b\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\bH\u00c6\u0003J-\u0010\u0013\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00062\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\u000e\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u0004J\t\u0010\u001a\u001a\u00020\u0004H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001c"}, d2 = {"Lcom/pawsup/billing/Entitlements;", "", "ownedCatIds", "", "", "hasCafeBundle", "", "lastSyncedAt", "", "(Ljava/util/Set;ZJ)V", "getHasCafeBundle", "()Z", "getLastSyncedAt", "()J", "getOwnedCatIds", "()Ljava/util/Set;", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "owns", "catId", "toString", "Companion", "app_debug"})
public final class Entitlements {
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.String> ownedCatIds = null;
    private final boolean hasCafeBundle = false;
    private final long lastSyncedAt = 0L;
    @org.jetbrains.annotations.NotNull()
    private static final com.pawsup.billing.Entitlements DEFAULT = null;
    private static final long OFFLINE_FALLBACK_DAYS = 14L;
    private static final long MS_PER_DAY = 86400000L;
    @org.jetbrains.annotations.NotNull()
    public static final com.pawsup.billing.Entitlements.Companion Companion = null;
    
    public Entitlements(@org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> ownedCatIds, boolean hasCafeBundle, long lastSyncedAt) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> getOwnedCatIds() {
        return null;
    }
    
    public final boolean getHasCafeBundle() {
        return false;
    }
    
    public final long getLastSyncedAt() {
        return 0L;
    }
    
    public final boolean owns(@org.jetbrains.annotations.NotNull()
    java.lang.String catId) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> component1() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    public final long component3() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.billing.Entitlements copy(@org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> ownedCatIds, boolean hasCafeBundle, long lastSyncedAt) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000b\u001a\u00020\bR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/pawsup/billing/Entitlements$Companion;", "", "()V", "DEFAULT", "Lcom/pawsup/billing/Entitlements;", "getDEFAULT", "()Lcom/pawsup/billing/Entitlements;", "MS_PER_DAY", "", "OFFLINE_FALLBACK_DAYS", "offlineFallback", "lastSyncedAt", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.pawsup.billing.Entitlements getDEFAULT() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.pawsup.billing.Entitlements offlineFallback(long lastSyncedAt) {
            return null;
        }
    }
}