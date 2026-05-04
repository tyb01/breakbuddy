package com.pawsup.android.ui.screens.customize;

import com.pawsup.android.data.repository.SettingsRepository;
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
public final class CustomizeViewModel_Factory implements Factory<CustomizeViewModel> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public CustomizeViewModel_Factory(Provider<SettingsRepository> settingsRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public CustomizeViewModel get() {
    return newInstance(settingsRepositoryProvider.get());
  }

  public static CustomizeViewModel_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new CustomizeViewModel_Factory(settingsRepositoryProvider);
  }

  public static CustomizeViewModel newInstance(SettingsRepository settingsRepository) {
    return new CustomizeViewModel(settingsRepository);
  }
}
