package com.pawsup.billing;

import com.pawsup.data.UserPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class BillingRepository_Factory implements Factory<BillingRepository> {
  private final Provider<BillingClientWrapper> billingClientProvider;

  private final Provider<UserPreferences> prefsProvider;

  public BillingRepository_Factory(Provider<BillingClientWrapper> billingClientProvider,
      Provider<UserPreferences> prefsProvider) {
    this.billingClientProvider = billingClientProvider;
    this.prefsProvider = prefsProvider;
  }

  @Override
  public BillingRepository get() {
    return newInstance(billingClientProvider.get(), prefsProvider.get());
  }

  public static BillingRepository_Factory create(
      Provider<BillingClientWrapper> billingClientProvider,
      Provider<UserPreferences> prefsProvider) {
    return new BillingRepository_Factory(billingClientProvider, prefsProvider);
  }

  public static BillingRepository newInstance(BillingClientWrapper billingClient,
      UserPreferences prefs) {
    return new BillingRepository(billingClient, prefs);
  }
}
