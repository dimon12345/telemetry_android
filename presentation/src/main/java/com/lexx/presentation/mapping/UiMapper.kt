package com.lexx.presentation.mapping

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.lexx.domain.features.sensors.local.SensorLocalInfo
import com.lexx.domain.models.SensorInfo
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
                name = it.name,
                remoteName = it.name,
                lastValue = it.lastValue,
                lastTimestamp = it.lastTimestamp,
            )
            val localInfo = localInfoMap[it.nameId]
            if ( localInfo != null) {
                uiInfo.copy(
                    color = mapStringToColor(localInfo.color),
                    remoteName = localInfo.name,
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
}
