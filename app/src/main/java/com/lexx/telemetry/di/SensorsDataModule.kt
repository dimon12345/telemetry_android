package com.lexx.telemetry.di

import com.lexx.telemetry.network.SensorsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL =
    "http://192.168.0.166:8080"

/**
 * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

@Module
@InstallIn(SingletonComponent::class)
object SensorsDataModule {
    @Provides
    @Singleton
    fun provideSensorsApiService() : SensorsApiService = retrofit.create(SensorsApiService::class.java)
}