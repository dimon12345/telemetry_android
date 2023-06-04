package com.lexx.telemetry.model

data class SensorData(
    val valueId: Int,
    val nameId: Int,
    val value: Float,
    val timestamp: String
)
