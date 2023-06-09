package com.lexx.domain.features.sensors

import com.lexx.domain.models.SensorInfo
import javax.inject.Inject

class GetSensorsInfoUseCase @Inject constructor(
    private val repository: SensorsRepository,
) {
    suspend operator fun invoke() : List<SensorInfo> {
        return repository.getSensorsInfo()
    }
}
