package com.pawsup.android.ui.navigation;

import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import com.pawsup.android.data.repository.SettingsRepository;
import com.pawsup.android.R;
import dagger.hilt.android.EntryPointAccessors;
import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&\u00a8\u0006\u0004"}, d2 = {"Lcom/pawsup/android/ui/navigation/SettingsRepositoryEntryPoint;", "", "settingsRepository", "Lcom/pawsup/android/data/repository/SettingsRepository;", "app_debug"})
@dagger.hilt.EntryPoint()
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract interface SettingsRepositoryEntryPoint {
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.pawsup.android.data.repository.SettingsRepository settingsRepository();
}