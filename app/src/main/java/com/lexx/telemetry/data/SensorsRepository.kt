package com.lexx.telemetry.data

import androidx.compose.ui.graphics.Color
import com.lexx.telemetry.model.SensorData
import com.lexx.telemetry.model.SensorInfo
import com.lexx.telemetry.network.SensorsApiService
import com.lexx.telemetry.ui.plot.PlotInfo
import com.lexx.telemetry.ui.plot.PlotLineInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SensorsRepository @Inject constructor(
    private val sensorsApiService: SensorsApiService,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend fun getSensorsInfo(): List<SensorInfo> {
        val serverAddress = userPreferencesRepository.getServerAddress().getOrNull() ?: ""
        return sensorsApiService.getSensorsInfo("http://$serverAddress/sensors")
    }

    suspend fun getPlotInfo(): PlotInfo {
        var serverAddress: String = userPreferencesRepository.getServerAddress().getOrNull() ?: ""
        try {
            val sensorsData = sensorsApiService.getSensorsData("http://$serverAddress/data")
            return convertSensorsData(sensorsData)
        } catch (e: Exception) {
            return PlotInfo(errorMessage = e.localizedMessage)
        }
    }

    private val redColor = Color(1.0f, .0f, .0f)
    private val greenColor = Color(.0f, 1.0f, .0f)
    private val blueColor = Color(.0f, .0f, 1.0f)
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    private fun convertSensorsData(sensorsData: List<SensorData>): PlotInfo {
        if (sensorsData.isEmpty()) {
            return PlotInfo()
        }

        val points: MutableMap<Int, MutableList<Pair<Float, LocalDateTime>>> = mutableMapOf()
        var minValue = Float.MAX_VALUE
        var maxValue = Float.MIN_VALUE
        var minTimestamp = convertTimestamp(sensorsData[0].timestamp)
        var maxTimestamp = minTimestamp

        for (data in sensorsData) {
            if (data.nameId !in points) {
                points[data.nameId] = mutableListOf()
            }

            val ts = convertTimestamp(data.timestamp)
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

    fun convertTimestamp(ts: String) : LocalDateTime {
        return LocalDateTime.parse(ts.split(".")[0], dateFormatter)
    }
}
