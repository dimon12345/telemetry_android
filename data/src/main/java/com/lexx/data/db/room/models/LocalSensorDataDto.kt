package com.lexx.data.db.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sensors")
data class LocalSensorDataDto (
    @PrimaryKey() val remoteSensorId: Int,
    val color: String,
    val name: String,
)
