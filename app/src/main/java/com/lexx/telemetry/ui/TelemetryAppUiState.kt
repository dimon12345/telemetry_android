package com.lexx.telemetry.ui

import com.lexx.telemetry.ui.navigation.NavigationAppContentType

data class TelemetryAppUiState(
    val name: String,
    val currentTelemetryAppContent: NavigationAppContentType
)
