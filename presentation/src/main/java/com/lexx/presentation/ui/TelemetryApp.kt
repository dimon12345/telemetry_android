package com.lexx.presentation.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lexx.presentation.ui.home_screen.TelemetryHomeScreen
import com.lexx.presentation.ui.navigation.NavigationType

@Composable
fun TelemetryApp(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
) {
    val navigationType = when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            NavigationType.BOTTOM_NAVIGATION
        }
        else -> {
            NavigationType.NAVIGATION_RAIL
        }
    }

    TelemetryHomeScreen(
        modifier = modifier,
        navigationType = navigationType,
    )
}
