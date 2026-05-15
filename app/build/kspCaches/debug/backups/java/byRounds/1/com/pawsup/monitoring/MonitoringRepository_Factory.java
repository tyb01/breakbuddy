package com.pawsup.monitoring;

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
public final class MonitoringRepository_Factory implements Factory<MonitoringRepository> {
  @Override
  public MonitoringRepository get() {
    return newInstance();
  }

  public static MonitoringRepository_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static MonitoringRepository newInstance() {
    return new MonitoringRepository();
  }

  private static final class InstanceHolder {
    private static final MonitoringRepository_Factory INSTANCE = new MonitoringRepository_Factory();
  }
}
