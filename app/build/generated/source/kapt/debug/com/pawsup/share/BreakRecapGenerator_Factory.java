package com.pawsup.share;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class BreakRecapGenerator_Factory implements Factory<BreakRecapGenerator> {
  @Override
  public BreakRecapGenerator get() {
    return newInstance();
  }

  public static BreakRecapGenerator_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static BreakRecapGenerator newInstance() {
    return new BreakRecapGenerator();
  }

  private static final class InstanceHolder {
    private static final BreakRecapGenerator_Factory INSTANCE = new BreakRecapGenerator_Factory();
  }
}
