package com.lexx.data.mappers

import com.lexx.data.api.telemetry.models.SensorInfoDto
import com.lexx.domain.models.SensorInfo
import javax.inject.Inject


class WebServiceDataMapper @Inject constructor() {
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
}