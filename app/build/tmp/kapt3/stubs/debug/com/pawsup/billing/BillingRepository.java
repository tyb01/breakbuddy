package com.pawsup.billing;

import com.android.billingclient.api.Purchase;
import com.pawsup.data.UserPreferences;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0082@\u00a2\u0006\u0002\u0010\u0012J\u000e\u0010\u0013\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\u00020\u000f2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0082@\u00a2\u0006\u0002\u0010\u0019R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/pawsup/billing/BillingRepository;", "", "billingClient", "Lcom/pawsup/billing/BillingClientWrapper;", "prefs", "Lcom/pawsup/data/UserPreferences;", "(Lcom/pawsup/billing/BillingClientWrapper;Lcom/pawsup/data/UserPreferences;)V", "entitlements", "Lkotlinx/coroutines/flow/Flow;", "Lcom/pawsup/billing/Entitlements;", "getEntitlements", "()Lkotlinx/coroutines/flow/Flow;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "handlePurchase", "", "productId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "refreshPurchases", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncPurchasesToPrefs", "purchases", "", "Lcom/android/billingclient/api/Purchase;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class BillingRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.billing.BillingClientWrapper billingClient = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.data.UserPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<com.pawsup.billing.Entitlements> entitlements = null;
    
    @javax.inject.Inject()
    public BillingRepository(@org.jetbrains.annotations.NotNull()
    com.pawsup.billing.BillingClientWrapper billingClient, @org.jetbrains.annotations.NotNull()
    com.pawsup.data.UserPreferences prefs) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.pawsup.billing.Entitlements> getEntitlements() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object refreshPurchases(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object syncPurchasesToPrefs(java.util.List<? extends com.android.billingclient.api.Purchase> purchases, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object handlePurchase(java.lang.String productId, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}