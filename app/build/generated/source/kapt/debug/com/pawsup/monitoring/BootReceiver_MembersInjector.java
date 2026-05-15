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
public final class BootReceiver_MembersInjector implements MembersInjector<BootReceiver> {
  private final Provider<UserPreferences> prefsProvider;

  public BootReceiver_MembersInjector(Provider<UserPreferences> prefsProvider) {
    this.prefsProvider = prefsProvider;
  }

  public static MembersInjector<BootReceiver> create(Provider<UserPreferences> prefsProvider) {
    return new BootReceiver_MembersInjector(prefsProvider);
  }

  @Override
  public void injectMembers(BootReceiver instance) {
    injectPrefs(instance, prefsProvider.get());
  }

  @InjectedFieldSignature("com.pawsup.monitoring.BootReceiver.prefs")
  public static void injectPrefs(BootReceiver instance, UserPreferences prefs) {
    instance.prefs = prefs;
  }
}
