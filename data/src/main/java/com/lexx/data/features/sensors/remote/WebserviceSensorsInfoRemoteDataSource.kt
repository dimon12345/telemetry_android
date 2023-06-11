package com.lexx.data.features.sensors.remote

import com.lexx.data.api.telemetry.TelemetryApiService
import com.lexx.data.api.telemetry.models.SensorInfoDto
import com.lexx.data.features.sensors.SensorsInfoRemoteDataSource
import javax.inject.Inject

class WebserviceSensorsInfoRemoteDataSource @Inject constructor(
    private val telemetryApiService: TelemetryApiService,
    private val BASE_URL : String
) : SensorsInfoRemoteDataSource {

    override suspend fun getSensors(): List<SensorInfoDto> {
        return telemetryApiService.getSensorsInfo(("$BASE_URL/sensors"))
    }
}
