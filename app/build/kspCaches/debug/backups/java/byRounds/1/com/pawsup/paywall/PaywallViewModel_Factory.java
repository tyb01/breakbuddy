package com.pawsup.paywall;

import androidx.lifecycle.SavedStateHandle;
import com.pawsup.billing.BillingClientWrapper;
import com.pawsup.billing.BillingRepository;
import com.pawsup.cats.CatRegistry;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class PaywallViewModel_Factory implements Factory<PaywallViewModel> {
  private final Provider<CatRegistry> catRegistryProvider;

  private final Provider<BillingClientWrapper> billingClientProvider;

  private final Provider<BillingRepository> billingRepoProvider;

  private final Provider<SavedStateHandle> savedStateProvider;

  public PaywallViewModel_Factory(Provider<CatRegistry> catRegistryProvider,
      Provider<BillingClientWrapper> billingClientProvider,
      Provider<BillingRepository> billingRepoProvider,
      Provider<SavedStateHandle> savedStateProvider) {
    this.catRegistryProvider = catRegistryProvider;
    this.billingClientProvider = billingClientProvider;
    this.billingRepoProvider = billingRepoProvider;
    this.savedStateProvider = savedStateProvider;
  }

  @Override
  public PaywallViewModel get() {
    return newInstance(catRegistryProvider.get(), billingClientProvider.get(), billingRepoProvider.get(), savedStateProvider.get());
  }

  public static PaywallViewModel_Factory create(Provider<CatRegistry> catRegistryProvider,
      Provider<BillingClientWrapper> billingClientProvider,
      Provider<BillingRepository> billingRepoProvider,
      Provider<SavedStateHandle> savedStateProvider) {
    return new PaywallViewModel_Factory(catRegistryProvider, billingClientProvider, billingRepoProvider, savedStateProvider);
  }

  public static PaywallViewModel newInstance(CatRegistry catRegistry,
      BillingClientWrapper billingClient, BillingRepository billingRepo,
      SavedStateHandle savedState) {
    return new PaywallViewModel(catRegistry, billingClient, billingRepo, savedState);
  }
}
