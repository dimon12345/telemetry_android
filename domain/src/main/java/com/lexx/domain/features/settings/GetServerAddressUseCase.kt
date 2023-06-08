package com.lexx.domain.features.settings

import javax.inject.Inject

class GetServerAddressUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(): String {
        return repository.getServerAddress()
    }
}
