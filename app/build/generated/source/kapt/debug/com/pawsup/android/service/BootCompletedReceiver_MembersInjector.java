package com.pawsup.android.service;

import com.pawsup.android.data.repository.SettingsRepository;
import com.pawsup.android.util.PermissionHelper;
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
public final class BootCompletedReceiver_MembersInjector implements MembersInjector<BootCompletedReceiver> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<PermissionHelper> permissionHelperProvider;

  public BootCompletedReceiver_MembersInjector(
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<PermissionHelper> permissionHelperProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.permissionHelperProvider = permissionHelperProvider;
  }

  public static MembersInjector<BootCompletedReceiver> create(
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<PermissionHelper> permissionHelperProvider) {
    return new BootCompletedReceiver_MembersInjector(settingsRepositoryProvider, permissionHelperProvider);
  }

  @Override
  public void injectMembers(BootCompletedReceiver instance) {
    injectSettingsRepository(instance, settingsRepositoryProvider.get());
    injectPermissionHelper(instance, permissionHelperProvider.get());
  }

  @InjectedFieldSignature("com.pawsup.android.service.BootCompletedReceiver.settingsRepository")
  public static void injectSettingsRepository(BootCompletedReceiver instance,
      SettingsRepository settingsRepository) {
    instance.settingsRepository = settingsRepository;
  }

  @InjectedFieldSignature("com.pawsup.android.service.BootCompletedReceiver.permissionHelper")
  public static void injectPermissionHelper(BootCompletedReceiver instance,
      PermissionHelper permissionHelper) {
    instance.permissionHelper = permissionHelper;
  }
}
