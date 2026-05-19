package com.pawsup.monitoring;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.pawsup.data.UserPreferences;
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
public final class WatchdogWorker_Factory {
  private final Provider<MonitoringRepository> repoProvider;

  private final Provider<UserPreferences> prefsProvider;

  public WatchdogWorker_Factory(Provider<MonitoringRepository> repoProvider,
      Provider<UserPreferences> prefsProvider) {
    this.repoProvider = repoProvider;
    this.prefsProvider = prefsProvider;
  }

  public WatchdogWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, repoProvider.get(), prefsProvider.get());
  }

  public static WatchdogWorker_Factory create(Provider<MonitoringRepository> repoProvider,
      Provider<UserPreferences> prefsProvider) {
    return new WatchdogWorker_Factory(repoProvider, prefsProvider);
  }

  public static WatchdogWorker newInstance(Context context, WorkerParameters params,
      MonitoringRepository repo, UserPreferences prefs) {
    return new WatchdogWorker(context, params, repo, prefs);
  }
}
