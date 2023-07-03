package com.lexx.presentation.mapping

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.lexx.domain.features.sensors.local.SensorLocalInfo
import com.lexx.domain.models.PlotData
import com.lexx.domain.models.PlotInfo
import com.lexx.domain.models.PlotLineInfo
import com.lexx.domain.models.SensorInfo
import com.lexx.presentation.models.PlotLineUiInfo
import com.lexx.presentation.models.PlotUiData
import com.lexx.presentation.models.PlotUiInfo
import com.lexx.presentation.models.SensorUiInfo
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class UiMapper @Inject constructor(
    private val sensorValueFormatter: DecimalFormat
) {
    fun mapValueToScreen(fl: Float): String {
        return sensorValueFormatter.format(fl)
    }

    fun mapTimestampToScreen(timestamp: Long): String {
        try {
            val sdf = SimpleDateFormat("HH:mm")
            val netDate = Date(timestamp * 1000 - 6 * 60 * 60 * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    fun mapColorToString(color: Color): String {
        val hexColor = java.lang.String.format("#%08X", color.toArgb())
        return hexColor
    }

    fun mapSensorInfoToUi(sensorsInfo: List<SensorInfo>, sensorsLocalInfo: List<SensorLocalInfo>): List<SensorUiInfo> {
        val localInfoMap: Map<Int, SensorLocalInfo> = sensorsLocalInfo.map {
            it.remoteSensorId to it
        }.toMap()

        val result = sensorsInfo.map {
            var uiInfo = SensorUiInfo(
                nameId = it.nameId,
                description = it.name,
                remoteName = it.name,
                lastValue = it.lastValue,
                lastTimestamp = it.lastTimestamp,
            )
            val localInfo = localInfoMap[it.nameId]
            if ( localInfo != null) {
                uiInfo.copy(
                    color = mapStringToColor(localInfo.color),
                    description = localInfo.description,
                )
            } else {
                uiInfo
            }
        }
        return result
    }

    private fun mapStringToColor(color: String): Color {
        val result = Color(android.graphics.Color.parseColor(color))
        return result
    }

    fun mapPlotInfoToUi(plotInfo: PlotInfo, localInfo: Map<Int, SensorLocalInfo>): PlotUiInfo {
        return PlotUiInfo(
            values = mapLinesToUi(plotInfo.values, localInfo),
            minValue = plotInfo.minValue,
            maxValue = plotInfo.maxValue,
            minTimestamp = plotInfo.minTimestamp,
            maxTimestamp = plotInfo.maxTimestamp,
            connectionError = plotInfo.errorMessage.isNotEmpty(),
            errorMessage = plotInfo.errorMessage,
            noDataError = plotInfo.values.isEmpty(),
        )
    }

    private fun mapLinesToUi(
        lines: List<PlotLineInfo>,
        localInfo: Map<Int, SensorLocalInfo>
    ): List<PlotLineUiInfo> {

        return lines.map {plotInfo ->
            val color = mapStringToColor(localInfo[plotInfo.nameId]?.color ?: "#FF000000")
            val values = mapValuesToUi(plotInfo.values)
            PlotLineUiInfo(
                nameId = plotInfo.nameId,
                values = values,
                color = color,
            )
        }
    }

    private fun mapValuesToUi(values: List<PlotData>): List<PlotUiData> {
        return values.map {
            mapPlotDataToUi(it)
        }
    }

    private fun mapPlotDataToUi(plotData: PlotData): PlotUiData {
        return PlotUiData(
            value = plotData.value,
            timestamp = plotData.timestamp
        )
    }

    fun mapSensorLocalInfoFromUi(selectedSensorInfo: SensorUiInfo): SensorLocalInfo {
        return with(selectedSensorInfo) {
            SensorLocalInfo (
                remoteSensorId = nameId,
                color = mapColorToString(color),
                description = description,
                enabled = enabled
            )
        }
    }
}
