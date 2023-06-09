package com.lexx.domain.features.sensors

import com.lexx.domain.models.SensorInfo

interface SensorsRepository {
    suspend fun getSensorsInfo(): List<SensorInfo>
}
