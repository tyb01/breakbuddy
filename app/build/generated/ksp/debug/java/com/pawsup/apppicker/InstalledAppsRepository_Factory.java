package com.pawsup.apppicker;

import android.content.pm.PackageManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class InstalledAppsRepository_Factory implements Factory<InstalledAppsRepository> {
  private final Provider<PackageManager> pmProvider;

  public InstalledAppsRepository_Factory(Provider<PackageManager> pmProvider) {
    this.pmProvider = pmProvider;
  }

  @Override
  public InstalledAppsRepository get() {
    return newInstance(pmProvider.get());
  }

  public static InstalledAppsRepository_Factory create(Provider<PackageManager> pmProvider) {
    return new InstalledAppsRepository_Factory(pmProvider);
  }

  public static InstalledAppsRepository newInstance(PackageManager pm) {
    return new InstalledAppsRepository(pm);
  }
}
