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
public final class KeepAliveReceiver_MembersInjector implements MembersInjector<KeepAliveReceiver> {
  private final Provider<UserPreferences> prefsProvider;

  private final Provider<MonitoringRepository> repoProvider;

  public KeepAliveReceiver_MembersInjector(Provider<UserPreferences> prefsProvider,
      Provider<MonitoringRepository> repoProvider) {
    this.prefsProvider = prefsProvider;
    this.repoProvider = repoProvider;
  }

  public static MembersInjector<KeepAliveReceiver> create(Provider<UserPreferences> prefsProvider,
      Provider<MonitoringRepository> repoProvider) {
    return new KeepAliveReceiver_MembersInjector(prefsProvider, repoProvider);
  }

  @Override
  public void injectMembers(KeepAliveReceiver instance) {
    injectPrefs(instance, prefsProvider.get());
    injectRepo(instance, repoProvider.get());
  }

  @InjectedFieldSignature("com.pawsup.monitoring.KeepAliveReceiver.prefs")
  public static void injectPrefs(KeepAliveReceiver instance, UserPreferences prefs) {
    instance.prefs = prefs;
  }

  @InjectedFieldSignature("com.pawsup.monitoring.KeepAliveReceiver.repo")
  public static void injectRepo(KeepAliveReceiver instance, MonitoringRepository repo) {
    instance.repo = repo;
  }
}
