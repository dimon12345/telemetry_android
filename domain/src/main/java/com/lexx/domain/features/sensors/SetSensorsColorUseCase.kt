package com.lexx.domain.features.sensors

import javax.inject.Inject

class SetSensorsColorUseCase @Inject constructor(
    private val localRepository: SensorsLocalRepository,
) {
    operator fun invoke(sensorId: Int, color: String) = localRepository.setSensorInfo(sensorId, color, "")
}
