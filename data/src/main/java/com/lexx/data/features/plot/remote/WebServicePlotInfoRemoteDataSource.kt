package com.lexx.data.features.plot.remote

import com.lexx.data.api.telemetry.TelemetryApiService
import com.lexx.data.api.telemetry.models.SensorDataDto
import com.lexx.data.features.plot.PlotInfoRemoteDataSource
import com.lexx.domain.PLOT_SECONDS_REFRESH_PERIOD
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class WebServicePlotInfoRemoteDataSource @Inject constructor(
    private val telemetryApiService: TelemetryApiService,
) : PlotInfoRemoteDataSource {

    private var paused = false
    private var hourPaused = false
    private var sixHoursPaused = true
    private var dayPaused = true
    override fun pauseNetworkPolling(pause: Boolean) {
        paused = pause
    }

    override fun pauseHourPolling(pause: Boolean) {
        hourPaused = pause
    }

    override fun pauseSixHoursPolling(pause: Boolean) {
        sixHoursPaused = pause
    }

    override fun pauseDayPolling(pause: Boolean) {
        dayPaused = pause
    }

    private enum class IntervalType {
        HOUR_INTERVAL,
        SIX_HOURS_INTERVAL,
        DAY_INTERVAL
    }

    private fun createHourSensorsDataFlow(interval: String) : Flow<Result<List<SensorDataDto>>> = flow {

        val intervalType = if (interval == "sixHours") {
            IntervalType.SIX_HOURS_INTERVAL
        } else if (interval == "day") {
            IntervalType.DAY_INTERVAL
        } else {
            IntervalType.HOUR_INTERVAL
        }

        while(true) {
            val intervalPaused = when(intervalType) {
                IntervalType.HOUR_INTERVAL -> hourPaused
                IntervalType.SIX_HOURS_INTERVAL -> sixHoursPaused
                IntervalType.DAY_INTERVAL -> dayPaused
            }

            if (!paused && !intervalPaused) {
                try {
                    val sensorsData = telemetryApiService.getSensorsData(interval)
                    emit(Result.success(sensorsData))
                } catch (e: UnknownHostException) {
                    emit(Result.failure(e))
                } catch (e: ConnectException) {
                    emit(Result.failure(e))
                } catch (e: SocketTimeoutException) {
                    emit(Result.failure(e))
                } catch (e: HttpException) {
                    emit(Result.failure(e))
                } catch (e: KotlinNullPointerException) {
                    emit(Result.failure(e))
                } catch (e: Exception) {
                    Timber.d("get remote SensorData exception: $e")
                    throw e
                }
            }
            delay(PLOT_SECONDS_REFRESH_PERIOD * 1000L)
        }
    }

    override val hourSensorsData: Flow<Result<List<SensorDataDto>>> = createHourSensorsDataFlow("hour")
    override val sixHoursSensorsData: Flow<Result<List<SensorDataDto>>> = createHourSensorsDataFlow("sixHours")
    override val daySensorsData: Flow<Result<List<SensorDataDto>>> = createHourSensorsDataFlow("day")
}
