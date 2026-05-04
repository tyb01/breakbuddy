package com.pawsup.android;

import androidx.hilt.work.HiltWorkerFactory;
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
public final class PawsUpApp_MembersInjector implements MembersInjector<PawsUpApp> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public PawsUpApp_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<PawsUpApp> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new PawsUpApp_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(PawsUpApp instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.pawsup.android.PawsUpApp.workerFactory")
  public static void injectWorkerFactory(PawsUpApp instance, HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
