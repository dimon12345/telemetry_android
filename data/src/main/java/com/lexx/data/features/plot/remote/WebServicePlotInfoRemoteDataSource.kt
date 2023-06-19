package com.lexx.data.features.plot.remote

import com.lexx.data.api.telemetry.TelemetryApiService
import com.lexx.data.api.telemetry.models.SensorDataDto
import com.lexx.data.features.plot.PlotInfoRemoteDataSource
import com.lexx.domain.features.settings.SettingsRepository
import javax.inject.Inject

class WebServicePlotInfoRemoteDataSource @Inject constructor(
    private val telemetryApiService: TelemetryApiService,
) : PlotInfoRemoteDataSource {
    override suspend fun getSensorsData(): List<SensorDataDto> =
        telemetryApiService.getSensorsData()
}
