package com.lexx.presentation.ui.sensors

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexx.domain.features.sensors.GetSensorsInfoUseCase
import com.lexx.domain.features.sensors.GetSensorsLocalInfoUseCase
import com.lexx.domain.features.sensors.SetSensorsColorUseCase
import com.lexx.domain.features.sensors.local.SensorLocalInfo
import com.lexx.domain.models.SensorInfo
import com.lexx.presentation.mapping.UiMapper
import com.lexx.presentation.models.SensorUiInfo
import com.lexx.presentation.models.SensorsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SensorsViewModel @Inject constructor(
    val getSensorsInfoUseCase: GetSensorsInfoUseCase,
    val getSensorsLocalInfoUseCase: GetSensorsLocalInfoUseCase,
    val setSensorsColorUseCase: SetSensorsColorUseCase,
    val uiMapper: UiMapper,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SensorsUiState())
    val uiState: StateFlow<SensorsUiState> = _uiState.asStateFlow()

    init {
        observeSensorsInfo()
    }

    private fun observeSensorsInfo() {
        viewModelScope.launch {
            getSensorsInfoUseCase()
                .combine(getSensorsLocalInfoUseCase()) { info: Result<List<SensorInfo>>, localInfo: List<SensorLocalInfo> ->
                    if (info.isSuccess) {
                        uiMapper.mapSensorInfoToUi(info.getOrDefault(listOf()), localInfo)
                    } else {
                        listOf()
                    }
                }.collect {
                    if (it.isEmpty()) {
                        _uiState.value = _uiState.value.copy(noSensorsError = true)
                    } else {
                        _uiState.value = _uiState.value.copy(
                            noSensorsError = false,
                            connectionError = false,
                            sensors = it
                        )
                    }
                }
        }
    }

    fun onSensorClick(sensorInfo: SensorUiInfo) {
        _uiState.value = _uiState.value.copy(
            selectedSensorInfo = sensorInfo,
            showEditor = true
        )
    }

    fun onCloseEditor() {
        _uiState.value = _uiState.value.copy(
            showEditor = false
        )
    }

    fun onColorChanged(color: Color) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val selectedId = _uiState.value.selectedSensorInfo.nameId
                setSensorsColorUseCase(selectedId, uiMapper.mapColorToString(color))
                _uiState.value =
                    _uiState.value.copy(sensors = _uiState.value.sensors.map{
                        if (it.nameId == selectedId) {
                            it.copy(color = color)
                        }
                        it
                    })
                Timber.d("here color check")
            }
        }
    }
}
