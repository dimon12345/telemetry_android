package com.lexx.telemetry.ui.sensors

import com.lexx.telemetry.model.SensorInfo

data class SensorsUiState(
    val sensors: List<SensorInfo> = listOf(),
    val errorText: String = ""
)
