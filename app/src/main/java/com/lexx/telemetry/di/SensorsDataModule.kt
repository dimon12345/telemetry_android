package com.lexx.telemetry.di

import com.lexx.telemetry.network.SensorsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SensorsDataModule {
    @Provides
    fun providesBaseUrl() : String = "http://192.168.0.166:8080"

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL : String) : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideSensorsApiService(retrofit: Retrofit) : SensorsApiService = retrofit.create(SensorsApiService::class.java)
}
