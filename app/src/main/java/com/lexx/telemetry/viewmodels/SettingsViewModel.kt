package com.lexx.telemetry.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lexx.telemetry.data.UserPreferencesRepository
import com.lexx.telemetry.ui.settings.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState(serverAddress = DEFAULT_SERVER_ADDRESS))
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            val serverAddress = userPreferencesRepository.getServerAddress(DEFAULT_SERVER_ADDRESS).getOrNull() ?: DEFAULT_SERVER_ADDRESS
            _uiState.value = _uiState.value.copy(serverAddress = serverAddress)
        }
    }

    fun setServerAddress(serverAddress: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(serverAddress = serverAddress)
            userPreferencesRepository.setServerAddress(serverAddress)
        }
    }

    companion object {
        private const val DEFAULT_SERVER_ADDRESS = "192.168.0.166:9090"
    }
}
