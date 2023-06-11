package com.lexx.telemetry.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexx.domain.features.sensors.GetSensorsInfoUseCase
import com.lexx.telemetry.ui.sensors.SensorsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SensorsViewModel @Inject constructor(
    val getSensorsInfoUseCase: GetSensorsInfoUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SensorsUiState())
    val uiState: StateFlow<SensorsUiState> = _uiState.asStateFlow()

    init {
        observeSensorsInfo()
    }

    fun observeSensorsInfo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(errorText = "")
            _uiState.value = _uiState.value.copy(sensors = getSensorsInfoUseCase())
        }
    }
}
