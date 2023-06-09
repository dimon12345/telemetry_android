package com.lexx.data.mappers

import com.lexx.data.api.telemetry.models.SensorDataDto
import com.lexx.data.api.telemetry.models.SensorInfoDto
import com.lexx.domain.models.SensorData
import com.lexx.domain.models.SensorInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


class WebServiceDataMapper @Inject constructor() {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun mapSensors(sensorsInfo: List<SensorInfoDto>): List<SensorInfo> {
        return sensorsInfo.map {
            mapSensor(sensorInfo = it)
        }
    }

    fun mapSensor(sensorInfo: SensorInfoDto): SensorInfo {
        return with(sensorInfo) {
            SensorInfo(
                nameId = nameId,
                name = name
            )
        }
    }

    fun convertTimestamp(ts: String) : LocalDateTime {
        return LocalDateTime.parse(ts.split(".")[0], dateFormatter)
    }

    fun convertSensorsData(sensorsData: List<SensorDataDto>): List<SensorData> {
        return sensorsData.map {
            mapSensorData(sensorData = it)
        }
    }

    private fun mapSensorData(sensorData: SensorDataDto): SensorData {
        return with(sensorData) {
            SensorData(
                valueId = valueId,
                nameId = nameId,
                value = value,
                timestamp = convertTimestamp(timestamp)
            )
        }
    }

//    fun convertSensorsData(sensorsData: List<SensorData>): PlotInfo {
//
//    }
}