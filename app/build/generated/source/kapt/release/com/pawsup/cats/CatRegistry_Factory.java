package com.pawsup.cats;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class CatRegistry_Factory implements Factory<CatRegistry> {
  @Override
  public CatRegistry get() {
    return newInstance();
  }

  public static CatRegistry_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CatRegistry newInstance() {
    return new CatRegistry();
  }

  private static final class InstanceHolder {
    private static final CatRegistry_Factory INSTANCE = new CatRegistry_Factory();
  }
}
