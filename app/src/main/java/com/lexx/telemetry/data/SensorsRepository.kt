package com.lexx.telemetry.data

import android.util.Log
import com.lexx.telemetry.model.SensorInfo
import com.lexx.telemetry.network.SensorsApiService
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorsRepository @Inject constructor(
    private val sensorsApiService: SensorsApiService,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend fun getSensorsInfo(): List<SensorInfo> {
        val serverAddress = userPreferencesRepository.getServerAddress().getOrNull() ?: ""
        return sensorsApiService.getSensorsInfo("http://$serverAddress/sensors")
    }
}
