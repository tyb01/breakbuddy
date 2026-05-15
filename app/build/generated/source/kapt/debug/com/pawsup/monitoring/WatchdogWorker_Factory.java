package com.pawsup.monitoring;

import android.content.Context;
import androidx.work.WorkerParameters;
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

  public WatchdogWorker_Factory(Provider<MonitoringRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  public WatchdogWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, repoProvider.get());
  }

  public static WatchdogWorker_Factory create(Provider<MonitoringRepository> repoProvider) {
    return new WatchdogWorker_Factory(repoProvider);
  }

  public static WatchdogWorker newInstance(Context context, WorkerParameters params,
      MonitoringRepository repo) {
    return new WatchdogWorker(context, params, repo);
  }
}
