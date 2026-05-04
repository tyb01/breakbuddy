package com.pawsup.android.domain.usecase;

import com.pawsup.android.data.repository.SettingsRepository;
import com.pawsup.android.data.repository.UsageRepository;
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
public final class CheckUsageLimitUseCase_Factory implements Factory<CheckUsageLimitUseCase> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<UsageRepository> usageRepositoryProvider;

  public CheckUsageLimitUseCase_Factory(Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<UsageRepository> usageRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.usageRepositoryProvider = usageRepositoryProvider;
  }

  @Override
  public CheckUsageLimitUseCase get() {
    return newInstance(settingsRepositoryProvider.get(), usageRepositoryProvider.get());
  }

  public static CheckUsageLimitUseCase_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<UsageRepository> usageRepositoryProvider) {
    return new CheckUsageLimitUseCase_Factory(settingsRepositoryProvider, usageRepositoryProvider);
  }

  public static CheckUsageLimitUseCase newInstance(SettingsRepository settingsRepository,
      UsageRepository usageRepository) {
    return new CheckUsageLimitUseCase(settingsRepository, usageRepository);
  }
}
