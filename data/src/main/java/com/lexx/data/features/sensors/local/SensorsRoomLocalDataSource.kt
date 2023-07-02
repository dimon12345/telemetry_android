package com.lexx.data.features.sensors.local

import com.lexx.data.db.room.models.LocalSensorDataDto
import com.lexx.data.features.sensors.SensorsLocalDataSource
import com.lexx.data.mappers.RoomDataMapper
import com.lexx.domain.features.sensors.local.SensorLocalInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SensorsRoomLocalDataSource @Inject constructor(
    val localSensorDataDao: LocalSensorDataDao,
    val roomDataMapper: RoomDataMapper,
) : SensorsLocalDataSource {

    override fun getSensorsInfo(): Flow<List<SensorLocalInfo>> {
        return localSensorDataDao.getAllSensors().map{
            roomDataMapper.mapSensorsData(it)
        }
    }

    override fun setSensorInfo(sensorId: Int, color: String, name: String) {
        localSensorDataDao.setSensorInfo(
            LocalSensorDataDto(sensorId, color, name)
        )
    }
}
