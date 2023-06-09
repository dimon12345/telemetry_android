package com.lexx.telemetry.data

import com.lexx.data.api.telemetry.TelemetryApiService
import com.lexx.data.mappers.WebServiceDataMapper
import com.lexx.domain.features.settings.SettingsRepository
import com.lexx.domain.models.SensorData
import com.lexx.telemetry.ui.plot.PlotInfo
import com.lexx.telemetry.ui.plot.PlotLineInfo
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SensorsRepositoryOld @Inject constructor(
    private val telemetryApiService: TelemetryApiService,
    private val settingsRepository: SettingsRepository,
    private val mapper: WebServiceDataMapper
) {
    suspend fun getPlotInfo(): PlotInfo {
        var serverAddress: String = settingsRepository.getServerAddress()
        try {
            val sensorsData = telemetryApiService.getSensorsData("http://$serverAddress/data")
            return convertSensorsData(mapper.convertSensorsData(sensorsData))
        } catch (e: Exception) {
            return PlotInfo(errorMessage = e.localizedMessage ?: "")
        }
    }

    private fun convertSensorsData(sensorsData: List<SensorData>): PlotInfo {
        if (sensorsData.isEmpty()) {
            return PlotInfo()
        }

        val points: MutableMap<Int, MutableList<Pair<Float, LocalDateTime>>> = mutableMapOf()
        var minValue = Float.MAX_VALUE
        var maxValue = Float.MIN_VALUE
        var minTimestamp = sensorsData[0].timestamp
        var maxTimestamp = minTimestamp

        for (data in sensorsData) {
            if (data.nameId !in points) {
                points[data.nameId] = mutableListOf()
            }

            val ts = data.timestamp
            points[data.nameId]?.add(Pair(data.value, ts))

            if (maxValue < data.value) {
                maxValue = data.value
            }

            if (minValue > data.value) {
                minValue = data.value
            }

            if (maxTimestamp < ts) {
                maxTimestamp = ts
            }

            if (minTimestamp > ts) {
                minTimestamp = ts
            }
        }

        val values: MutableMap<Int, PlotLineInfo> = mutableMapOf()
        points.forEach { entry ->
            values[entry.key] = PlotLineInfo(points = entry.value)
        }
        return PlotInfo(
            values = values,
            minValue = minValue,
            maxValue = maxValue,
            minTimestamp = minTimestamp,
            maxTimestamp = maxTimestamp
        )
    }

}
