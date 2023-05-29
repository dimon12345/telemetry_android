package com.lexx.telemetry.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexx.telemetry.data.SensorsRepository
import com.lexx.telemetry.model.SensorInfo
import com.lexx.telemetry.ui.TelemetryAppUiState
import com.lexx.telemetry.ui.navigation.NavigationAppContentType
import com.lexx.telemetry.ui.sensors.SensorsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SensorsViewModel @Inject constructor(
    val repository: SensorsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SensorsUiState())
    val uiState: StateFlow<SensorsUiState> = _uiState.asStateFlow()

    init {
        observeSensorsInfo()
    }

    fun observeSensorsInfo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(errorText = "")
            try {
                _uiState.value = _uiState.value.copy(sensors = repository.getSensorsInfo())
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorText = e.localizedMessage)
            }
        }
    }
}
