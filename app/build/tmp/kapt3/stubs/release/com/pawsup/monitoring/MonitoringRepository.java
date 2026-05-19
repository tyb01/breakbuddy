package com.pawsup.monitoring;

import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0005\"\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/pawsup/monitoring/MonitoringRepository;", "", "()V", "isServiceRunning", "", "()Z", "setServiceRunning", "(Z)V", "app_release"})
public final class MonitoringRepository {
    @kotlin.jvm.Volatile()
    private volatile boolean isServiceRunning = false;
    
    @javax.inject.Inject()
    public MonitoringRepository() {
        super();
    }
    
    public final boolean isServiceRunning() {
        return false;
    }
    
    public final void setServiceRunning(boolean p0) {
    }
}