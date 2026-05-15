package com.pawsup.settings;

import android.content.Context;
import com.pawsup.billing.BillingRepository;
import com.pawsup.cats.CatRegistry;
import com.pawsup.data.UserPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<UserPreferences> prefsProvider;

  private final Provider<CatRegistry> catRegistryProvider;

  private final Provider<BillingRepository> billingRepoProvider;

  private final Provider<Context> contextProvider;

  public SettingsViewModel_Factory(Provider<UserPreferences> prefsProvider,
      Provider<CatRegistry> catRegistryProvider, Provider<BillingRepository> billingRepoProvider,
      Provider<Context> contextProvider) {
    this.prefsProvider = prefsProvider;
    this.catRegistryProvider = catRegistryProvider;
    this.billingRepoProvider = billingRepoProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(prefsProvider.get(), catRegistryProvider.get(), billingRepoProvider.get(), contextProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<UserPreferences> prefsProvider,
      Provider<CatRegistry> catRegistryProvider, Provider<BillingRepository> billingRepoProvider,
      Provider<Context> contextProvider) {
    return new SettingsViewModel_Factory(prefsProvider, catRegistryProvider, billingRepoProvider, contextProvider);
  }

  public static SettingsViewModel newInstance(UserPreferences prefs, CatRegistry catRegistry,
      BillingRepository billingRepo, Context context) {
    return new SettingsViewModel(prefs, catRegistry, billingRepo, context);
  }
}
