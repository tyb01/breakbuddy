package com.pawsup.android.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.pawsup.android.data.repository.SettingsRepository;
import com.pawsup.android.util.PermissionHelper;
import dagger.internal.DaggerGenerated;
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
public final class UsageWatchdogWorker_Factory {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<PermissionHelper> permissionHelperProvider;

  public UsageWatchdogWorker_Factory(Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<PermissionHelper> permissionHelperProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.permissionHelperProvider = permissionHelperProvider;
  }

  public UsageWatchdogWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, settingsRepositoryProvider.get(), permissionHelperProvider.get());
  }

  public static UsageWatchdogWorker_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<PermissionHelper> permissionHelperProvider) {
    return new UsageWatchdogWorker_Factory(settingsRepositoryProvider, permissionHelperProvider);
  }

  public static UsageWatchdogWorker newInstance(Context context, WorkerParameters params,
      SettingsRepository settingsRepository, PermissionHelper permissionHelper) {
    return new UsageWatchdogWorker(context, params, settingsRepository, permissionHelper);
  }
}
