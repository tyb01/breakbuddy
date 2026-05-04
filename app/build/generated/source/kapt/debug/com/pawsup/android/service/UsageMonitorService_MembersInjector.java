package com.pawsup.android.service;

import com.pawsup.android.data.repository.SettingsRepository;
import com.pawsup.android.data.repository.UsageRepository;
import com.pawsup.android.domain.usecase.CheckUsageLimitUseCase;
import com.pawsup.android.util.PermissionHelper;
import com.pawsup.android.util.UsageStatsHelper;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class UsageMonitorService_MembersInjector implements MembersInjector<UsageMonitorService> {
  private final Provider<UsageStatsHelper> usageStatsHelperProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<UsageRepository> usageRepositoryProvider;

  private final Provider<CheckUsageLimitUseCase> checkUsageLimitUseCaseProvider;

  private final Provider<OverlayManager> overlayManagerProvider;

  private final Provider<PermissionHelper> permissionHelperProvider;

  public UsageMonitorService_MembersInjector(Provider<UsageStatsHelper> usageStatsHelperProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<UsageRepository> usageRepositoryProvider,
      Provider<CheckUsageLimitUseCase> checkUsageLimitUseCaseProvider,
      Provider<OverlayManager> overlayManagerProvider,
      Provider<PermissionHelper> permissionHelperProvider) {
    this.usageStatsHelperProvider = usageStatsHelperProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.usageRepositoryProvider = usageRepositoryProvider;
    this.checkUsageLimitUseCaseProvider = checkUsageLimitUseCaseProvider;
    this.overlayManagerProvider = overlayManagerProvider;
    this.permissionHelperProvider = permissionHelperProvider;
  }

  public static MembersInjector<UsageMonitorService> create(
      Provider<UsageStatsHelper> usageStatsHelperProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<UsageRepository> usageRepositoryProvider,
      Provider<CheckUsageLimitUseCase> checkUsageLimitUseCaseProvider,
      Provider<OverlayManager> overlayManagerProvider,
      Provider<PermissionHelper> permissionHelperProvider) {
    return new UsageMonitorService_MembersInjector(usageStatsHelperProvider, settingsRepositoryProvider, usageRepositoryProvider, checkUsageLimitUseCaseProvider, overlayManagerProvider, permissionHelperProvider);
  }

  @Override
  public void injectMembers(UsageMonitorService instance) {
    injectUsageStatsHelper(instance, usageStatsHelperProvider.get());
    injectSettingsRepository(instance, settingsRepositoryProvider.get());
    injectUsageRepository(instance, usageRepositoryProvider.get());
    injectCheckUsageLimitUseCase(instance, checkUsageLimitUseCaseProvider.get());
    injectOverlayManager(instance, overlayManagerProvider.get());
    injectPermissionHelper(instance, permissionHelperProvider.get());
  }

  @InjectedFieldSignature("com.pawsup.android.service.UsageMonitorService.usageStatsHelper")
  public static void injectUsageStatsHelper(UsageMonitorService instance,
      UsageStatsHelper usageStatsHelper) {
    instance.usageStatsHelper = usageStatsHelper;
  }

  @InjectedFieldSignature("com.pawsup.android.service.UsageMonitorService.settingsRepository")
  public static void injectSettingsRepository(UsageMonitorService instance,
      SettingsRepository settingsRepository) {
    instance.settingsRepository = settingsRepository;
  }

  @InjectedFieldSignature("com.pawsup.android.service.UsageMonitorService.usageRepository")
  public static void injectUsageRepository(UsageMonitorService instance,
      UsageRepository usageRepository) {
    instance.usageRepository = usageRepository;
  }

  @InjectedFieldSignature("com.pawsup.android.service.UsageMonitorService.checkUsageLimitUseCase")
  public static void injectCheckUsageLimitUseCase(UsageMonitorService instance,
      CheckUsageLimitUseCase checkUsageLimitUseCase) {
    instance.checkUsageLimitUseCase = checkUsageLimitUseCase;
  }

  @InjectedFieldSignature("com.pawsup.android.service.UsageMonitorService.overlayManager")
  public static void injectOverlayManager(UsageMonitorService instance,
      OverlayManager overlayManager) {
    instance.overlayManager = overlayManager;
  }

  @InjectedFieldSignature("com.pawsup.android.service.UsageMonitorService.permissionHelper")
  public static void injectPermissionHelper(UsageMonitorService instance,
      PermissionHelper permissionHelper) {
    instance.permissionHelper = permissionHelper;
  }
}
