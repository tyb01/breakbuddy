package com.pawsup.android.ui;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<PermissionHelper> permissionHelperProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public MainActivity_MembersInjector(Provider<PermissionHelper> permissionHelperProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.permissionHelperProvider = permissionHelperProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<PermissionHelper> permissionHelperProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new MainActivity_MembersInjector(permissionHelperProvider, settingsRepositoryProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectPermissionHelper(instance, permissionHelperProvider.get());
    injectSettingsRepository(instance, settingsRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.pawsup.android.ui.MainActivity.permissionHelper")
  public static void injectPermissionHelper(MainActivity instance,
      PermissionHelper permissionHelper) {
    instance.permissionHelper = permissionHelper;
  }

  @InjectedFieldSignature("com.pawsup.android.ui.MainActivity.settingsRepository")
  public static void injectSettingsRepository(MainActivity instance,
      SettingsRepository settingsRepository) {
    instance.settingsRepository = settingsRepository;
  }
}
