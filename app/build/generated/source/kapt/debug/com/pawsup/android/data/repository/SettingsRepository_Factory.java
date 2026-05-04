package com.pawsup.android.data.repository;

import android.content.Context;
import com.pawsup.android.data.datastore.SettingsDataStore;
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
public final class SettingsRepository_Factory implements Factory<SettingsRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<SettingsDataStore> settingsDataStoreProvider;

  public SettingsRepository_Factory(Provider<Context> contextProvider,
      Provider<SettingsDataStore> settingsDataStoreProvider) {
    this.contextProvider = contextProvider;
    this.settingsDataStoreProvider = settingsDataStoreProvider;
  }

  @Override
  public SettingsRepository get() {
    return newInstance(contextProvider.get(), settingsDataStoreProvider.get());
  }

  public static SettingsRepository_Factory create(Provider<Context> contextProvider,
      Provider<SettingsDataStore> settingsDataStoreProvider) {
    return new SettingsRepository_Factory(contextProvider, settingsDataStoreProvider);
  }

  public static SettingsRepository newInstance(Context context,
      SettingsDataStore settingsDataStore) {
    return new SettingsRepository(context, settingsDataStore);
  }
}
