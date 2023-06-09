package com.lexx.data.features.sensors.remote

import com.lexx.data.api.telemetry.TelemetryApiService
import com.lexx.data.features.sensors.RemoteSensorsDataSource
import com.lexx.data.mappers.WebServiceDataMapper
import com.lexx.domain.models.SensorInfo
import javax.inject.Inject

class WebserviceRemoteSensorsDataSource @Inject constructor(
    private val telemetryApiService: TelemetryApiService,
    private val BASE_URL : String,
    private val mapper: WebServiceDataMapper
) : RemoteSensorsDataSource {

    override suspend fun getSensors(): List<SensorInfo> {
        return mapper.mapSensors(telemetryApiService.getSensorsInfo(("$BASE_URL/sensors")))
    }
}
