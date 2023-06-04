package com.lexx.telemetry.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexx.telemetry.data.SensorsRepository
import com.lexx.telemetry.ui.plot.PlotInfo
import com.lexx.telemetry.ui.plot.PlotUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class PlotViewModel @Inject constructor(
    val sensorsRepository: SensorsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlotUiState())
    val uiState: StateFlow<PlotUiState> = _uiState.asStateFlow()

    init {
        loadSensors()
    }

    private fun loadSensors() {
        viewModelScope.launch {
            try {

                val plotInfo = sensorsRepository.getPlotInfo()
                _uiState.value = _uiState.value.copy(plotInfo = plotInfo)
            } catch (e: UnknownHostException) {
                _uiState.value = _uiState.value.copy(plotInfo = PlotInfo(errorMessage = e.localizedMessage))
            }
        }
    }
}
