package com.lexx.telemetry.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SensorData(
    @SerialName("value_id")
    val valueId: String,

    @SerialName("name_id")
    val nameId: String,

    val value: Float,

    val timestamp: String
)
