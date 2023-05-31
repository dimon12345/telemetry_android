package com.lexx.telemetry.data

import com.lexx.telemetry.model.SensorData
import com.lexx.telemetry.model.SensorInfo
import com.lexx.telemetry.network.SensorsApiService
import java.util.Date
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

    suspend fun getSensorsData(): Map<String, List<Pair<Float, Date>>> {
        val serverAddress = userPreferencesRepository.getServerAddress().getOrNull() ?: ""
        val sensorsData = sensorsApiService.getSensorsData("http://$serverAddress/data")
        return convertSensorsData(sensorsData)
    }

    private fun convertSensorsData(sensorsData: List<SensorData>): Map<String, List<Pair<Float, Date>>> {
        return mapOf()
    }
}
