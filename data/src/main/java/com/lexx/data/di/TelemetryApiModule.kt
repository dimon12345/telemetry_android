package com.lexx.data.di

import com.lexx.data.BuildConfig
import com.lexx.data.api.telemetry.TelemetryApiService
import com.lexx.domain.features.settings.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TelemetryApiModule {

    @Provides
    fun providesBaseUrl() : String = BuildConfig.DEFAULT_BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(
        BASE_URL : String,
        settingsRepository: SettingsRepository
    ) : Retrofit {
        var client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                var request: Request = chain.request()
                lateinit var serverAddress: String
                runBlocking {
                    serverAddress = settingsRepository.getServerAddress(BuildConfig.DEFAULT_BASE_URL)
                }
                val serverPath: String = request.url().encodedPath()
                val url = "http://${serverAddress}${serverPath}"
                val newRequest = request
                    .newBuilder()
                    .url(url)
                    .build()
                chain.proceed(newRequest)
            }
            .build()

        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://$BASE_URL")
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideTelemetryApiService(retrofit: Retrofit) : TelemetryApiService = retrofit.create(TelemetryApiService::class.java)
}
