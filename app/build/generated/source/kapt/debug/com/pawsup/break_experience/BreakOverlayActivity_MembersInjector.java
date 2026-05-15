package com.pawsup.break_experience;

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
public final class BreakOverlayActivity_MembersInjector implements MembersInjector<BreakOverlayActivity> {
  private final Provider<CatRegistry> catRegistryProvider;

  public BreakOverlayActivity_MembersInjector(Provider<CatRegistry> catRegistryProvider) {
    this.catRegistryProvider = catRegistryProvider;
  }

  public static MembersInjector<BreakOverlayActivity> create(
      Provider<CatRegistry> catRegistryProvider) {
    return new BreakOverlayActivity_MembersInjector(catRegistryProvider);
  }

  @Override
  public void injectMembers(BreakOverlayActivity instance) {
    injectCatRegistry(instance, catRegistryProvider.get());
  }

  @InjectedFieldSignature("com.pawsup.break_experience.BreakOverlayActivity.catRegistry")
  public static void injectCatRegistry(BreakOverlayActivity instance, CatRegistry catRegistry) {
    instance.catRegistry = catRegistry;
  }
}
