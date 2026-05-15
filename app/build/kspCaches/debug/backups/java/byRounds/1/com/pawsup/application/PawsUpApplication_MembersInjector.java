package com.pawsup.application;

import androidx.hilt.work.HiltWorkerFactory;
import com.pawsup.billing.BillingClientWrapper;
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
public final class PawsUpApplication_MembersInjector implements MembersInjector<PawsUpApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  private final Provider<BillingClientWrapper> billingClientProvider;

  public PawsUpApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<BillingClientWrapper> billingClientProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
    this.billingClientProvider = billingClientProvider;
  }

  public static MembersInjector<PawsUpApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<BillingClientWrapper> billingClientProvider) {
    return new PawsUpApplication_MembersInjector(workerFactoryProvider, billingClientProvider);
  }

  @Override
  public void injectMembers(PawsUpApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
    injectBillingClient(instance, billingClientProvider.get());
  }

  @InjectedFieldSignature("com.pawsup.application.PawsUpApplication.workerFactory")
  public static void injectWorkerFactory(PawsUpApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }

  @InjectedFieldSignature("com.pawsup.application.PawsUpApplication.billingClient")
  public static void injectBillingClient(PawsUpApplication instance,
      BillingClientWrapper billingClient) {
    instance.billingClient = billingClient;
  }
}
