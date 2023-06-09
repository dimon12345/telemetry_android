package com.lexx.telemetry.di

import com.lexx.telemetry.network.SensorsApiServiceOld
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SensorsDataModule {
    @Provides
    @Singleton
    fun provideSensorsApiService(retrofit: Retrofit) : SensorsApiServiceOld = retrofit.create(SensorsApiServiceOld::class.java)
}
