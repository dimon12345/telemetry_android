package com.lexx.domain.models

import java.time.LocalDateTime

data class SensorData(
    val valueId: Int,
    val nameId: Int,
    val value: Float,
    val timestamp: LocalDateTime
)
