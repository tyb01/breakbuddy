package com.pawsup.android.domain.usecase;

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
public final class SaveSettingsUseCase_Factory implements Factory<SaveSettingsUseCase> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public SaveSettingsUseCase_Factory(Provider<SettingsRepository> settingsRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public SaveSettingsUseCase get() {
    return newInstance(settingsRepositoryProvider.get());
  }

  public static SaveSettingsUseCase_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new SaveSettingsUseCase_Factory(settingsRepositoryProvider);
  }

  public static SaveSettingsUseCase newInstance(SettingsRepository settingsRepository) {
    return new SaveSettingsUseCase(settingsRepository);
  }
}
