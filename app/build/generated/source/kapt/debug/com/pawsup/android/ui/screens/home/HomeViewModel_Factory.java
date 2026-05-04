package com.pawsup.android.ui.screens.home;

import android.content.Context;
import com.pawsup.android.data.repository.SettingsRepository;
import com.pawsup.android.data.repository.UsageRepository;
import com.pawsup.android.domain.usecase.SaveSettingsUseCase;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<Context> appProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<UsageRepository> usageRepositoryProvider;

  private final Provider<SaveSettingsUseCase> saveSettingsProvider;

  private final Provider<PermissionHelper> permissionHelperProvider;

  public HomeViewModel_Factory(Provider<Context> appProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<UsageRepository> usageRepositoryProvider,
      Provider<SaveSettingsUseCase> saveSettingsProvider,
      Provider<PermissionHelper> permissionHelperProvider) {
    this.appProvider = appProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.usageRepositoryProvider = usageRepositoryProvider;
    this.saveSettingsProvider = saveSettingsProvider;
    this.permissionHelperProvider = permissionHelperProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(appProvider.get(), settingsRepositoryProvider.get(), usageRepositoryProvider.get(), saveSettingsProvider.get(), permissionHelperProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<Context> appProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<UsageRepository> usageRepositoryProvider,
      Provider<SaveSettingsUseCase> saveSettingsProvider,
      Provider<PermissionHelper> permissionHelperProvider) {
    return new HomeViewModel_Factory(appProvider, settingsRepositoryProvider, usageRepositoryProvider, saveSettingsProvider, permissionHelperProvider);
  }

  public static HomeViewModel newInstance(Context app, SettingsRepository settingsRepository,
      UsageRepository usageRepository, SaveSettingsUseCase saveSettings,
      PermissionHelper permissionHelper) {
    return new HomeViewModel(app, settingsRepository, usageRepository, saveSettings, permissionHelper);
  }
}
