package com.lexx.data.features.sensors

import com.lexx.domain.features.sensors.SensorsLocalRepository
import com.lexx.domain.features.sensors.local.SensorLocalInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SensorsRoomLocalRepository @Inject constructor(
    val sensorsLocalDataSource: SensorsLocalDataSource,
) : SensorsLocalRepository {
    override fun getSensorsInfo(): Flow<List<SensorLocalInfo>> {
        return sensorsLocalDataSource.getSensorsInfo()
    }

    override fun setSensorInfo(sensorId: Int, color: String, name: String) {
        sensorsLocalDataSource.setSensorInfo(sensorId, color, name)
    }
}

interface SensorsLocalDataSource {
    fun getSensorsInfo(): Flow<List<SensorLocalInfo>>
    fun setSensorInfo(sensorId: Int, color: String, name: String)
}
