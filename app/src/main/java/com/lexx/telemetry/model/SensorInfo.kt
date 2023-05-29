package com.lexx.telemetry.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class SensorInfo (
    @SerialName("name_id")
    val nameId: String,
    val name: String
)
