package com.lexx.telemetry.data

import com.lexx.telemetry.model.SensorInfo
import com.lexx.telemetry.network.SensorsApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorsRepository @Inject constructor(
    private val sensorsApiService: SensorsApiService
) {
    suspend fun getSensorsInfo(): List<SensorInfo> {
        return sensorsApiService.getSensorsInfo()
    }
}
