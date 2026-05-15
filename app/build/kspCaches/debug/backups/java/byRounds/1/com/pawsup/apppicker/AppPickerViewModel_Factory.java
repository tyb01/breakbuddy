package com.pawsup.apppicker;

import com.pawsup.data.UserPreferences;
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
public final class AppPickerViewModel_Factory implements Factory<AppPickerViewModel> {
  private final Provider<InstalledAppsRepository> appsRepoProvider;

  private final Provider<UserPreferences> prefsProvider;

  public AppPickerViewModel_Factory(Provider<InstalledAppsRepository> appsRepoProvider,
      Provider<UserPreferences> prefsProvider) {
    this.appsRepoProvider = appsRepoProvider;
    this.prefsProvider = prefsProvider;
  }

  @Override
  public AppPickerViewModel get() {
    return newInstance(appsRepoProvider.get(), prefsProvider.get());
  }

  public static AppPickerViewModel_Factory create(
      Provider<InstalledAppsRepository> appsRepoProvider, Provider<UserPreferences> prefsProvider) {
    return new AppPickerViewModel_Factory(appsRepoProvider, prefsProvider);
  }

  public static AppPickerViewModel newInstance(InstalledAppsRepository appsRepo,
      UserPreferences prefs) {
    return new AppPickerViewModel(appsRepo, prefs);
  }
}
