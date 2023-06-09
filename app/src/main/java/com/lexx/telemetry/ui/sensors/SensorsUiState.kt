package com.lexx.telemetry.ui.sensors

import com.lexx.domain.models.SensorInfo

data class SensorsUiState(
    val sensors: List<SensorInfo> = listOf(),
    val errorText: String = ""
)
