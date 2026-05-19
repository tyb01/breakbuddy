package com.pawsup.monitoring;

import com.pawsup.data.UserPreferences;
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
public final class MonitoringService_MembersInjector implements MembersInjector<MonitoringService> {
  private final Provider<UserPreferences> prefsProvider;

  private final Provider<MonitoringRepository> repoProvider;

  public MonitoringService_MembersInjector(Provider<UserPreferences> prefsProvider,
      Provider<MonitoringRepository> repoProvider) {
    this.prefsProvider = prefsProvider;
    this.repoProvider = repoProvider;
  }

  public static MembersInjector<MonitoringService> create(Provider<UserPreferences> prefsProvider,
      Provider<MonitoringRepository> repoProvider) {
    return new MonitoringService_MembersInjector(prefsProvider, repoProvider);
  }

  @Override
  public void injectMembers(MonitoringService instance) {
    injectPrefs(instance, prefsProvider.get());
    injectRepo(instance, repoProvider.get());
  }

  @InjectedFieldSignature("com.pawsup.monitoring.MonitoringService.prefs")
  public static void injectPrefs(MonitoringService instance, UserPreferences prefs) {
    instance.prefs = prefs;
  }

  @InjectedFieldSignature("com.pawsup.monitoring.MonitoringService.repo")
  public static void injectRepo(MonitoringService instance, MonitoringRepository repo) {
    instance.repo = repo;
  }
}
