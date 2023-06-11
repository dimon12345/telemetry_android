package com.lexx.data.features.plot

import com.lexx.data.api.telemetry.TelemetryApiService
import com.lexx.data.mappers.WebServiceDataMapper
import com.lexx.domain.features.plot.PlotRepository
import com.lexx.domain.features.settings.SettingsRepository
import com.lexx.domain.models.PlotInfo
import javax.inject.Inject

class PlotRemoteRepository @Inject constructor(
    private val telemetryApiService: TelemetryApiService,
    private val settingsRepository: SettingsRepository,
    private val mapper: WebServiceDataMapper,
) : PlotRepository {
    override suspend fun getPlotInfo(): PlotInfo {
        try {
            val serverAddress: String = settingsRepository.getServerAddress()
            val sensorsData = telemetryApiService.getSensorsData("http://$serverAddress/data")
            return mapper.mapPlotInfo(sensorsData)

        } catch (e: Exception) {
            return PlotInfo(errorMessage = e.localizedMessage ?: "")
        }
    }
}
