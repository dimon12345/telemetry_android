package com.lexx.data.features.sensors

import com.lexx.data.api.telemetry.models.SensorInfoDto
import com.lexx.data.mappers.WebServiceDataMapper
import com.lexx.domain.features.sensors.SensorsRepository
import com.lexx.domain.models.SensorInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SensorsInfoRepository @Inject constructor(
    private val sensorsInfoRemoteDataSource: SensorsInfoRemoteDataSource,
    private val mapper: WebServiceDataMapper,
) : SensorsRepository {
    override suspend fun getSensorsInfo(): List<SensorInfo> {
        val result = withContext(Dispatchers.Default) {
            sensorsInfoRemoteDataSource.getSensors()
        }

        return mapper.mapSensors(result)
    }
}

interface SensorsInfoRemoteDataSource {
    suspend fun getSensors(): List<SensorInfoDto>
}
