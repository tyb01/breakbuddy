package com.pawsup.monitoring

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MonitoringRepository @Inject constructor() {
    @Volatile var isServiceRunning: Boolean = false
}
