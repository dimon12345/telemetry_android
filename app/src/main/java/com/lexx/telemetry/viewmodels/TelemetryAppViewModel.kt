package com.lexx.telemetry.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexx.telemetry.data.UserPreferencesRepository
import com.lexx.telemetry.ui.TelemetryAppUiState
import com.lexx.telemetry.ui.navigation.NavigationAppContentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TelemetryAppViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TelemetryAppUiState("Noname", NavigationAppContentType.SENSORS_CONTENT_TYPE))
    val uiState: StateFlow<TelemetryAppUiState> = _uiState.asStateFlow()

    init {
        loadName()
    }

    private fun loadName() {
        viewModelScope.launch {
            val name = userPreferencesRepository.getName().getOrNull() ?: "Noname"
            _uiState.value = _uiState.value.copy(name=name)
        }
    }

    fun updateNavigationContent(navigationAppContentType: NavigationAppContentType) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(currentTelemetryAppContent = navigationAppContentType)
        }
    }
}
