package com.lexx.telemetry.network

import com.lexx.telemetry.model.SensorData
import com.lexx.telemetry.model.SensorInfo
import retrofit2.http.GET

interface SensorsApiService {
    @GET("sensors")
    suspend fun getSensorsInfo(): List<SensorInfo>

    @GET("data")
    suspend fun getSensorsData(): List<SensorData>
}
