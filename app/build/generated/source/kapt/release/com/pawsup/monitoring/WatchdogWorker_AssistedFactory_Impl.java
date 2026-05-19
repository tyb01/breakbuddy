package com.pawsup.monitoring;

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
public final class WatchdogWorker_AssistedFactory_Impl implements WatchdogWorker_AssistedFactory {
  private final WatchdogWorker_Factory delegateFactory;

  WatchdogWorker_AssistedFactory_Impl(WatchdogWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public WatchdogWorker create(Context arg0, WorkerParameters arg1) {
    return delegateFactory.get(arg0, arg1);
  }

  public static Provider<WatchdogWorker_AssistedFactory> create(
      WatchdogWorker_Factory delegateFactory) {
    return InstanceFactory.create(new WatchdogWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<WatchdogWorker_AssistedFactory> createFactoryProvider(
      WatchdogWorker_Factory delegateFactory) {
    return InstanceFactory.create(new WatchdogWorker_AssistedFactory_Impl(delegateFactory));
  }
}
