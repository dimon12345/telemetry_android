package com.lexx.data.di

import com.lexx.data.features.sensors.RemoteSensorsDataSource
import com.lexx.data.features.sensors.RemoteSensorsRepository
import com.lexx.data.features.sensors.remote.WebserviceRemoteSensorsDataSource
import com.lexx.data.features.settings.DataStoreSettingsRepository
import com.lexx.domain.features.sensors.SensorsRepository
import com.lexx.domain.features.settings.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindUserPreferencesRepository(dataStoreUserPreferencesRepository: DataStoreSettingsRepository): SettingsRepository

    @Binds
    abstract fun bindRemoteSensorsRepository(remoteSensorsRepository: RemoteSensorsRepository): SensorsRepository

    @Binds
    abstract fun bindRemoteSensorsDataSource(webserviceRemoteSensorsDataSource: WebserviceRemoteSensorsDataSource): RemoteSensorsDataSource
}
