package com.lexx.telemetry.network

import com.lexx.telemetry.model.SensorData
import retrofit2.http.GET
import retrofit2.http.Url

interface SensorsApiServiceOld {
    @GET
    suspend fun getSensorsData(@Url url: String): List<SensorData>
}
