package com.pawsup.break_experience;

import androidx.lifecycle.SavedStateHandle;
import com.pawsup.cats.CatRegistry;
import com.pawsup.data.UserPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class BreakOverlayViewModel_Factory implements Factory<BreakOverlayViewModel> {
  private final Provider<UserPreferences> prefsProvider;

  private final Provider<CatRegistry> catRegistryProvider;

  private final Provider<SavedStateHandle> savedStateProvider;

  public BreakOverlayViewModel_Factory(Provider<UserPreferences> prefsProvider,
      Provider<CatRegistry> catRegistryProvider, Provider<SavedStateHandle> savedStateProvider) {
    this.prefsProvider = prefsProvider;
    this.catRegistryProvider = catRegistryProvider;
    this.savedStateProvider = savedStateProvider;
  }

  @Override
  public BreakOverlayViewModel get() {
    return newInstance(prefsProvider.get(), catRegistryProvider.get(), savedStateProvider.get());
  }

  public static BreakOverlayViewModel_Factory create(Provider<UserPreferences> prefsProvider,
      Provider<CatRegistry> catRegistryProvider, Provider<SavedStateHandle> savedStateProvider) {
    return new BreakOverlayViewModel_Factory(prefsProvider, catRegistryProvider, savedStateProvider);
  }

  public static BreakOverlayViewModel newInstance(UserPreferences prefs, CatRegistry catRegistry,
      SavedStateHandle savedState) {
    return new BreakOverlayViewModel(prefs, catRegistry, savedState);
  }
}
