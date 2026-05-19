package com.pawsup.application;

import androidx.hilt.work.HiltWorkerFactory;
import com.pawsup.billing.BillingClientWrapper;
import com.pawsup.cats.CatRegistry;
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

  private final Provider<CatRegistry> catRegistryProvider;

  public PawsUpApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<BillingClientWrapper> billingClientProvider,
      Provider<CatRegistry> catRegistryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
    this.billingClientProvider = billingClientProvider;
    this.catRegistryProvider = catRegistryProvider;
  }

  public static MembersInjector<PawsUpApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<BillingClientWrapper> billingClientProvider,
      Provider<CatRegistry> catRegistryProvider) {
    return new PawsUpApplication_MembersInjector(workerFactoryProvider, billingClientProvider, catRegistryProvider);
  }

  @Override
  public void injectMembers(PawsUpApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
    injectBillingClient(instance, billingClientProvider.get());
    injectCatRegistry(instance, catRegistryProvider.get());
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

  @InjectedFieldSignature("com.pawsup.application.PawsUpApplication.catRegistry")
  public static void injectCatRegistry(PawsUpApplication instance, CatRegistry catRegistry) {
    instance.catRegistry = catRegistry;
  }
}
