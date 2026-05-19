package com.pawsup.application;

import android.app.Application;
import android.graphics.BitmapFactory;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.work.Configuration;
import com.pawsup.billing.BillingClientWrapper;
import com.pawsup.cats.CatAssetResolver;
import com.pawsup.cats.CatRegistry;
import com.pawsup.ui.CatPosterCache;
import dagger.hilt.android.HiltAndroidApp;
import kotlinx.coroutines.Dispatchers;
import javax.inject.Inject;

@dagger.hilt.android.HiltAndroidApp()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001bH\u0002R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001e\u0010\n\u001a\u00020\u000b8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001e\u0010\u0014\u001a\u00020\u00158\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001d"}, d2 = {"Lcom/pawsup/application/PawsUpApplication;", "Landroid/app/Application;", "Landroidx/work/Configuration$Provider;", "()V", "billingClient", "Lcom/pawsup/billing/BillingClientWrapper;", "getBillingClient", "()Lcom/pawsup/billing/BillingClientWrapper;", "setBillingClient", "(Lcom/pawsup/billing/BillingClientWrapper;)V", "catRegistry", "Lcom/pawsup/cats/CatRegistry;", "getCatRegistry", "()Lcom/pawsup/cats/CatRegistry;", "setCatRegistry", "(Lcom/pawsup/cats/CatRegistry;)V", "workManagerConfiguration", "Landroidx/work/Configuration;", "getWorkManagerConfiguration", "()Landroidx/work/Configuration;", "workerFactory", "Landroidx/hilt/work/HiltWorkerFactory;", "getWorkerFactory", "()Landroidx/hilt/work/HiltWorkerFactory;", "setWorkerFactory", "(Landroidx/hilt/work/HiltWorkerFactory;)V", "onCreate", "", "preloadCatPosters", "app_release"})
public final class PawsUpApplication extends android.app.Application implements androidx.work.Configuration.Provider {
    @javax.inject.Inject()
    public androidx.hilt.work.HiltWorkerFactory workerFactory;
    @javax.inject.Inject()
    public com.pawsup.billing.BillingClientWrapper billingClient;
    @javax.inject.Inject()
    public com.pawsup.cats.CatRegistry catRegistry;
    
    public PawsUpApplication() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.hilt.work.HiltWorkerFactory getWorkerFactory() {
        return null;
    }
    
    public final void setWorkerFactory(@org.jetbrains.annotations.NotNull()
    androidx.hilt.work.HiltWorkerFactory p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.billing.BillingClientWrapper getBillingClient() {
        return null;
    }
    
    public final void setBillingClient(@org.jetbrains.annotations.NotNull()
    com.pawsup.billing.BillingClientWrapper p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.cats.CatRegistry getCatRegistry() {
        return null;
    }
    
    public final void setCatRegistry(@org.jetbrains.annotations.NotNull()
    com.pawsup.cats.CatRegistry p0) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public androidx.work.Configuration getWorkManagerConfiguration() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    private final void preloadCatPosters() {
    }
}