package com.pawsup.android.ui.screens.onboarding;

import android.content.Context;
import com.pawsup.android.data.repository.SettingsRepository;
import com.pawsup.android.util.PermissionHelper;
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
public final class OnboardingViewModel_Factory implements Factory<OnboardingViewModel> {
  private final Provider<Context> appProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<PermissionHelper> permissionHelperProvider;

  public OnboardingViewModel_Factory(Provider<Context> appProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<PermissionHelper> permissionHelperProvider) {
    this.appProvider = appProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.permissionHelperProvider = permissionHelperProvider;
  }

  @Override
  public OnboardingViewModel get() {
    return newInstance(appProvider.get(), settingsRepositoryProvider.get(), permissionHelperProvider.get());
  }

  public static OnboardingViewModel_Factory create(Provider<Context> appProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<PermissionHelper> permissionHelperProvider) {
    return new OnboardingViewModel_Factory(appProvider, settingsRepositoryProvider, permissionHelperProvider);
  }

  public static OnboardingViewModel newInstance(Context app, SettingsRepository settingsRepository,
      PermissionHelper permissionHelper) {
    return new OnboardingViewModel(app, settingsRepository, permissionHelper);
  }
}
