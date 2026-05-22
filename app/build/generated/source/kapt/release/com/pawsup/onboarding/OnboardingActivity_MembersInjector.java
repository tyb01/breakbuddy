package com.pawsup.onboarding;

import com.pawsup.cats.CatRegistry;
import com.pawsup.data.UserPreferences;
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
public final class OnboardingActivity_MembersInjector implements MembersInjector<OnboardingActivity> {
  private final Provider<UserPreferences> prefsProvider;

  private final Provider<CatRegistry> catRegistryProvider;

  public OnboardingActivity_MembersInjector(Provider<UserPreferences> prefsProvider,
      Provider<CatRegistry> catRegistryProvider) {
    this.prefsProvider = prefsProvider;
    this.catRegistryProvider = catRegistryProvider;
  }

  public static MembersInjector<OnboardingActivity> create(Provider<UserPreferences> prefsProvider,
      Provider<CatRegistry> catRegistryProvider) {
    return new OnboardingActivity_MembersInjector(prefsProvider, catRegistryProvider);
  }

  @Override
  public void injectMembers(OnboardingActivity instance) {
    injectPrefs(instance, prefsProvider.get());
    injectCatRegistry(instance, catRegistryProvider.get());
  }

  @InjectedFieldSignature("com.pawsup.onboarding.OnboardingActivity.prefs")
  public static void injectPrefs(OnboardingActivity instance, UserPreferences prefs) {
    instance.prefs = prefs;
  }

  @InjectedFieldSignature("com.pawsup.onboarding.OnboardingActivity.catRegistry")
  public static void injectCatRegistry(OnboardingActivity instance, CatRegistry catRegistry) {
    instance.catRegistry = catRegistry;
  }
}
