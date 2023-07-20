package com.lexx.presentation.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.lexx.presentation.navigation.TelemetryAppNavigationType

@Composable
fun TelemetryApp(
    windowSize: WindowWidthSizeClass,
    viewModel: TelemetryAppViewModel,
    modifier: Modifier = Modifier
) {
    val telemetryAppUiState = viewModel.uiState.collectAsState().value

    val navigationType = when (windowSize) {
        WindowWidthSizeClass.Compact -> {TelemetryAppNavigationType.BOTTOM_NAVIGATION}
        else -> {TelemetryAppNavigationType.NAVIGATION_RAIL}
    }

    TelemetryHomeScreen(
        navigationType,
        telemetryAppUiState,
        onTabPressed = { navigationAppContentType ->
            viewModel.updateNavigationContent(navigationAppContentType)
        },
        modifier = modifier
    )
}
