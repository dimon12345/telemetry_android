package com.lexx.telemetry.ui

import com.lexx.presentation.navigation.NavigationAppContentType

data class TelemetryAppUiState(
    val name: String,
    val currentTelemetryAppContent: NavigationAppContentType
)
