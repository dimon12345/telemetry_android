package com.lexx.telemetry.network

import com.lexx.telemetry.model.SensorData
import com.lexx.telemetry.model.SensorInfo
import retrofit2.http.GET
import retrofit2.http.Url

interface SensorsApiService {
    @GET
    suspend fun getSensorsInfo(@Url url: String): List<SensorInfo>

    @GET("data")
    suspend fun getSensorsData(): List<SensorData>
}
