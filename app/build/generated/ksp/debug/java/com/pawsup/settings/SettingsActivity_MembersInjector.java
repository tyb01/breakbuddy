package com.pawsup.settings;

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
public final class SettingsActivity_MembersInjector implements MembersInjector<SettingsActivity> {
  private final Provider<CatRegistry> catRegistryProvider;

  public SettingsActivity_MembersInjector(Provider<CatRegistry> catRegistryProvider) {
    this.catRegistryProvider = catRegistryProvider;
  }

  public static MembersInjector<SettingsActivity> create(
      Provider<CatRegistry> catRegistryProvider) {
    return new SettingsActivity_MembersInjector(catRegistryProvider);
  }

  @Override
  public void injectMembers(SettingsActivity instance) {
    injectCatRegistry(instance, catRegistryProvider.get());
  }

  @InjectedFieldSignature("com.pawsup.settings.SettingsActivity.catRegistry")
  public static void injectCatRegistry(SettingsActivity instance, CatRegistry catRegistry) {
    instance.catRegistry = catRegistry;
  }
}
