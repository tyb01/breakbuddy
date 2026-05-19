package com.pawsup.apppicker;

import androidx.lifecycle.ViewModel;
import com.pawsup.data.UserPreferences;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\u000f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\nH\u00c6\u0003J=\u0010\u0019\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001a\u001a\u00020\n2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001J\t\u0010\u001e\u001a\u00020\u0007H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0010R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u001f"}, d2 = {"Lcom/pawsup/apppicker/AppPickerUiState;", "", "allApps", "", "Lcom/pawsup/apppicker/InstalledApp;", "monitoredPackages", "", "", "searchQuery", "isLoading", "", "(Ljava/util/List;Ljava/util/Set;Ljava/lang/String;Z)V", "getAllApps", "()Ljava/util/List;", "filteredApps", "getFilteredApps", "()Z", "getMonitoredPackages", "()Ljava/util/Set;", "getSearchQuery", "()Ljava/lang/String;", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "app_release"})
public final class AppPickerUiState {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.pawsup.apppicker.InstalledApp> allApps = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.String> monitoredPackages = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String searchQuery = null;
    private final boolean isLoading = false;
    
    public AppPickerUiState(@org.jetbrains.annotations.NotNull()
    java.util.List<com.pawsup.apppicker.InstalledApp> allApps, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> monitoredPackages, @org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery, boolean isLoading) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.pawsup.apppicker.InstalledApp> getAllApps() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> getMonitoredPackages() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSearchQuery() {
        return null;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.pawsup.apppicker.InstalledApp> getFilteredApps() {
        return null;
    }
    
    public AppPickerUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.pawsup.apppicker.InstalledApp> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.apppicker.AppPickerUiState copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.pawsup.apppicker.InstalledApp> allApps, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> monitoredPackages, @org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery, boolean isLoading) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}