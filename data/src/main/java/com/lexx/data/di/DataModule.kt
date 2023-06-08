package com.lexx.data.di

import com.lexx.data.features.settings.DataStoreUserPreferencesRepository
import com.lexx.domain.features.settings.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindUserPreferencesRepository(dataStoreUserPreferencesRepository: DataStoreUserPreferencesRepository): UserPreferencesRepository

}
