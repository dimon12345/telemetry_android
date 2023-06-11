package com.lexx.data.features.sensors

import com.lexx.data.api.telemetry.models.SensorInfoDto
import com.lexx.data.mappers.WebServiceDataMapper
import com.lexx.domain.features.sensors.SensorsRepository
import com.lexx.domain.models.SensorInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RemoteSensorsRepository @Inject constructor(
    private val remoteSensorsDataSource: RemoteSensorsDataSource,
    private val mapper: WebServiceDataMapper,
) : SensorsRepository {
    override suspend fun getSensorsInfo(): List<SensorInfo> {
        val result = withContext(Dispatchers.Default) {
            remoteSensorsDataSource.getSensors()
        }

        return mapper.mapSensors(result)
    }
}

interface RemoteSensorsDataSource {
    suspend fun getSensors(): List<SensorInfoDto>
}
