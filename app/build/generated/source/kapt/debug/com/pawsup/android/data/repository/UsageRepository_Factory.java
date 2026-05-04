package com.pawsup.android.data.repository;

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
public final class UsageRepository_Factory implements Factory<UsageRepository> {
  @Override
  public UsageRepository get() {
    return newInstance();
  }

  public static UsageRepository_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static UsageRepository newInstance() {
    return new UsageRepository();
  }

  private static final class InstanceHolder {
    private static final UsageRepository_Factory INSTANCE = new UsageRepository_Factory();
  }
}
