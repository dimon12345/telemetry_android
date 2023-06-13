package com.lexx.domain.models

data class SensorInfo (
    val nameId: Int,
    val name: String,
    val lastValue: Float,
    val lastTimestamp: Long,
)
