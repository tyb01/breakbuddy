package com.pawsup.android.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class UsageWatchdogWorker_AssistedFactory_Impl implements UsageWatchdogWorker_AssistedFactory {
  private final UsageWatchdogWorker_Factory delegateFactory;

  UsageWatchdogWorker_AssistedFactory_Impl(UsageWatchdogWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public UsageWatchdogWorker create(Context arg0, WorkerParameters arg1) {
    return delegateFactory.get(arg0, arg1);
  }

  public static Provider<UsageWatchdogWorker_AssistedFactory> create(
      UsageWatchdogWorker_Factory delegateFactory) {
    return InstanceFactory.create(new UsageWatchdogWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<UsageWatchdogWorker_AssistedFactory> createFactoryProvider(
      UsageWatchdogWorker_Factory delegateFactory) {
    return InstanceFactory.create(new UsageWatchdogWorker_AssistedFactory_Impl(delegateFactory));
  }
}
