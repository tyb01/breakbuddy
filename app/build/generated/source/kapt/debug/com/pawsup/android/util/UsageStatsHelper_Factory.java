package com.pawsup.android.util;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class UsageStatsHelper_Factory implements Factory<UsageStatsHelper> {
  private final Provider<Context> contextProvider;

  public UsageStatsHelper_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public UsageStatsHelper get() {
    return newInstance(contextProvider.get());
  }

  public static UsageStatsHelper_Factory create(Provider<Context> contextProvider) {
    return new UsageStatsHelper_Factory(contextProvider);
  }

  public static UsageStatsHelper newInstance(Context context) {
    return new UsageStatsHelper(context);
  }
}
