package com.lexx.data.features.sensors

import com.lexx.domain.features.sensors.SensorsRepository
import com.lexx.domain.models.SensorInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RemoteSensorsRepository @Inject constructor(
    private val remoteSensorsDataSource: RemoteSensorsDataSource,
) : SensorsRepository {
    override suspend fun getSensorsInfo(): List<SensorInfo> {
        val result = withContext(Dispatchers.Default) {
            remoteSensorsDataSource.getSensors()
        }

        return result
    }
}

interface RemoteSensorsDataSource {
    suspend fun getSensors(): List<SensorInfo>
}
