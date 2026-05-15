package com.pawsup.android;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.pawsup.android.data.datastore.SettingsDataStore;
import com.pawsup.android.data.repository.SettingsRepository;
import com.pawsup.android.data.repository.UsageRepository;
import com.pawsup.android.di.AppModule_ProvidePreferencesDataStoreFactory;
import com.pawsup.android.di.AppModule_ProvideSettingsDataStoreFactory;
import com.pawsup.android.domain.usecase.CheckUsageLimitUseCase;
import com.pawsup.android.domain.usecase.SaveSettingsUseCase;
import com.pawsup.android.service.BootCompletedReceiver;
import com.pawsup.android.service.BootCompletedReceiver_MembersInjector;
import com.pawsup.android.service.OverlayManager;
import com.pawsup.android.service.UsageMonitorService;
import com.pawsup.android.service.UsageMonitorService_MembersInjector;
import com.pawsup.android.ui.MainActivity;
import com.pawsup.android.ui.MainActivity_MembersInjector;
import com.pawsup.android.ui.screens.customize.CustomizeViewModel;
import com.pawsup.android.ui.screens.customize.CustomizeViewModel_HiltModules;
import com.pawsup.android.ui.screens.home.HomeViewModel;
import com.pawsup.android.ui.screens.home.HomeViewModel_HiltModules;
import com.pawsup.android.ui.screens.onboarding.OnboardingViewModel;
import com.pawsup.android.ui.screens.onboarding.OnboardingViewModel_HiltModules;
import com.pawsup.android.util.PermissionHelper;
import com.pawsup.android.util.UsageStatsHelper;
import com.pawsup.android.worker.UsageWatchdogWorker;
import com.pawsup.android.worker.UsageWatchdogWorker_AssistedFactory;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerPawsUpApp_HiltComponents_SingletonC {
  private DaggerPawsUpApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public PawsUpApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements PawsUpApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public PawsUpApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements PawsUpApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public PawsUpApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements PawsUpApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public PawsUpApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements PawsUpApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public PawsUpApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements PawsUpApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public PawsUpApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements PawsUpApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public PawsUpApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements PawsUpApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public PawsUpApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends PawsUpApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends PawsUpApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends PawsUpApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends PawsUpApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity arg0) {
      injectMainActivity2(arg0);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(3).put(LazyClassKeyProvider.com_pawsup_android_ui_screens_customize_CustomizeViewModel, CustomizeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_pawsup_android_ui_screens_home_HomeViewModel, HomeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_pawsup_android_ui_screens_onboarding_OnboardingViewModel, OnboardingViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectPermissionHelper(instance, singletonCImpl.permissionHelperProvider.get());
      MainActivity_MembersInjector.injectSettingsRepository(instance, singletonCImpl.settingsRepositoryProvider.get());
      return instance;
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_pawsup_android_ui_screens_customize_CustomizeViewModel = "com.pawsup.android.ui.screens.customize.CustomizeViewModel";

      static String com_pawsup_android_ui_screens_onboarding_OnboardingViewModel = "com.pawsup.android.ui.screens.onboarding.OnboardingViewModel";

      static String com_pawsup_android_ui_screens_home_HomeViewModel = "com.pawsup.android.ui.screens.home.HomeViewModel";

      @KeepFieldType
      CustomizeViewModel com_pawsup_android_ui_screens_customize_CustomizeViewModel2;

      @KeepFieldType
      OnboardingViewModel com_pawsup_android_ui_screens_onboarding_OnboardingViewModel2;

      @KeepFieldType
      HomeViewModel com_pawsup_android_ui_screens_home_HomeViewModel2;
    }
  }

  private static final class ViewModelCImpl extends PawsUpApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<CustomizeViewModel> customizeViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<OnboardingViewModel> onboardingViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private SaveSettingsUseCase saveSettingsUseCase() {
      return new SaveSettingsUseCase(singletonCImpl.settingsRepositoryProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.customizeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.onboardingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(3).put(LazyClassKeyProvider.com_pawsup_android_ui_screens_customize_CustomizeViewModel, ((Provider) customizeViewModelProvider)).put(LazyClassKeyProvider.com_pawsup_android_ui_screens_home_HomeViewModel, ((Provider) homeViewModelProvider)).put(LazyClassKeyProvider.com_pawsup_android_ui_screens_onboarding_OnboardingViewModel, ((Provider) onboardingViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_pawsup_android_ui_screens_onboarding_OnboardingViewModel = "com.pawsup.android.ui.screens.onboarding.OnboardingViewModel";

      static String com_pawsup_android_ui_screens_home_HomeViewModel = "com.pawsup.android.ui.screens.home.HomeViewModel";

      static String com_pawsup_android_ui_screens_customize_CustomizeViewModel = "com.pawsup.android.ui.screens.customize.CustomizeViewModel";

      @KeepFieldType
      OnboardingViewModel com_pawsup_android_ui_screens_onboarding_OnboardingViewModel2;

      @KeepFieldType
      HomeViewModel com_pawsup_android_ui_screens_home_HomeViewModel2;

      @KeepFieldType
      CustomizeViewModel com_pawsup_android_ui_screens_customize_CustomizeViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.pawsup.android.ui.screens.customize.CustomizeViewModel 
          return (T) new CustomizeViewModel(singletonCImpl.settingsRepositoryProvider.get());

          case 1: // com.pawsup.android.ui.screens.home.HomeViewModel 
          return (T) new HomeViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.settingsRepositoryProvider.get(), singletonCImpl.usageRepositoryProvider.get(), viewModelCImpl.saveSettingsUseCase(), singletonCImpl.permissionHelperProvider.get());

          case 2: // com.pawsup.android.ui.screens.onboarding.OnboardingViewModel 
          return (T) new OnboardingViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.settingsRepositoryProvider.get(), singletonCImpl.permissionHelperProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends PawsUpApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends PawsUpApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    private CheckUsageLimitUseCase checkUsageLimitUseCase() {
      return new CheckUsageLimitUseCase(singletonCImpl.settingsRepositoryProvider.get(), singletonCImpl.usageRepositoryProvider.get());
    }

    @Override
    public void injectUsageMonitorService(UsageMonitorService arg0) {
      injectUsageMonitorService2(arg0);
    }

    private UsageMonitorService injectUsageMonitorService2(UsageMonitorService instance) {
      UsageMonitorService_MembersInjector.injectUsageStatsHelper(instance, singletonCImpl.usageStatsHelperProvider.get());
      UsageMonitorService_MembersInjector.injectSettingsRepository(instance, singletonCImpl.settingsRepositoryProvider.get());
      UsageMonitorService_MembersInjector.injectUsageRepository(instance, singletonCImpl.usageRepositoryProvider.get());
      UsageMonitorService_MembersInjector.injectCheckUsageLimitUseCase(instance, checkUsageLimitUseCase());
      UsageMonitorService_MembersInjector.injectOverlayManager(instance, singletonCImpl.overlayManagerProvider.get());
      UsageMonitorService_MembersInjector.injectPermissionHelper(instance, singletonCImpl.permissionHelperProvider.get());
      return instance;
    }
  }

  private static final class SingletonCImpl extends PawsUpApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<DataStore<Preferences>> providePreferencesDataStoreProvider;

    private Provider<SettingsDataStore> provideSettingsDataStoreProvider;

    private Provider<SettingsRepository> settingsRepositoryProvider;

    private Provider<PermissionHelper> permissionHelperProvider;

    private Provider<UsageWatchdogWorker_AssistedFactory> usageWatchdogWorker_AssistedFactoryProvider;

    private Provider<UsageRepository> usageRepositoryProvider;

    private Provider<UsageStatsHelper> usageStatsHelperProvider;

    private Provider<OverlayManager> overlayManagerProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return Collections.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>singletonMap("com.pawsup.android.worker.UsageWatchdogWorker", ((Provider) usageWatchdogWorker_AssistedFactoryProvider));
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.providePreferencesDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<DataStore<Preferences>>(singletonCImpl, 3));
      this.provideSettingsDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<SettingsDataStore>(singletonCImpl, 2));
      this.settingsRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<SettingsRepository>(singletonCImpl, 1));
      this.permissionHelperProvider = DoubleCheck.provider(new SwitchingProvider<PermissionHelper>(singletonCImpl, 4));
      this.usageWatchdogWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<UsageWatchdogWorker_AssistedFactory>(singletonCImpl, 0));
      this.usageRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<UsageRepository>(singletonCImpl, 5));
      this.usageStatsHelperProvider = DoubleCheck.provider(new SwitchingProvider<UsageStatsHelper>(singletonCImpl, 6));
      this.overlayManagerProvider = DoubleCheck.provider(new SwitchingProvider<OverlayManager>(singletonCImpl, 7));
    }

    @Override
    public void injectPawsUpApp(PawsUpApp arg0) {
      injectPawsUpApp2(arg0);
    }

    @Override
    public void injectBootCompletedReceiver(BootCompletedReceiver arg0) {
      injectBootCompletedReceiver2(arg0);
    }

    @Override
    public SettingsRepository settingsRepository() {
      return settingsRepositoryProvider.get();
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private PawsUpApp injectPawsUpApp2(PawsUpApp instance) {
      PawsUpApp_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private BootCompletedReceiver injectBootCompletedReceiver2(BootCompletedReceiver instance) {
      BootCompletedReceiver_MembersInjector.injectSettingsRepository(instance, settingsRepositoryProvider.get());
      BootCompletedReceiver_MembersInjector.injectPermissionHelper(instance, permissionHelperProvider.get());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.pawsup.android.worker.UsageWatchdogWorker_AssistedFactory 
          return (T) new UsageWatchdogWorker_AssistedFactory() {
            @Override
            public UsageWatchdogWorker create(Context context, WorkerParameters params) {
              return new UsageWatchdogWorker(context, params, singletonCImpl.settingsRepositoryProvider.get(), singletonCImpl.permissionHelperProvider.get());
            }
          };

          case 1: // com.pawsup.android.data.repository.SettingsRepository 
          return (T) new SettingsRepository(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideSettingsDataStoreProvider.get());

          case 2: // com.pawsup.android.data.datastore.SettingsDataStore 
          return (T) AppModule_ProvideSettingsDataStoreFactory.provideSettingsDataStore(singletonCImpl.providePreferencesDataStoreProvider.get());

          case 3: // androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> 
          return (T) AppModule_ProvidePreferencesDataStoreFactory.providePreferencesDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.pawsup.android.util.PermissionHelper 
          return (T) new PermissionHelper(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.pawsup.android.data.repository.UsageRepository 
          return (T) new UsageRepository();

          case 6: // com.pawsup.android.util.UsageStatsHelper 
          return (T) new UsageStatsHelper(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 7: // com.pawsup.android.service.OverlayManager 
          return (T) new OverlayManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
