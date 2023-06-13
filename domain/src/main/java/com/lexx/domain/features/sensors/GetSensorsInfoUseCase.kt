package com.lexx.domain.features.sensors

import com.lexx.domain.models.SensorInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSensorsInfoUseCase @Inject constructor(
    private val repository: SensorsRepository,
) {
    suspend operator fun invoke() : Flow<List<SensorInfo>> {
        return repository.getSensorsInfo()
    }
}
