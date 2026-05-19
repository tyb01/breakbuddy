package com.pawsup.di;

import com.pawsup.cats.CatRegistry;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideCatRegistryFactory implements Factory<CatRegistry> {
  @Override
  public CatRegistry get() {
    return provideCatRegistry();
  }

  public static AppModule_ProvideCatRegistryFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CatRegistry provideCatRegistry() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideCatRegistry());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvideCatRegistryFactory INSTANCE = new AppModule_ProvideCatRegistryFactory();
  }
}
