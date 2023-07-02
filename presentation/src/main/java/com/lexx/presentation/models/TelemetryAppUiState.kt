package com.lexx.presentation.models

import androidx.compose.ui.graphics.Color
import com.lexx.presentation.navigation.NavigationAppContentType

data class TelemetryAppUiState(
    val currentTelemetryAppContent: NavigationAppContentType = NavigationAppContentType.SENSORS_CONTENT_TYPE,
)
