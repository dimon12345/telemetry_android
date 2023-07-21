package com.lexx.presentation.ui.home_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lexx.presentation.models.navigation.NavigationItemContent
import com.lexx.presentation.ui.navigation.AppContentType
import com.lexx.presentation.ui.navigation.NavigationType
import com.lexx.presentation.ui.sensors.SensorsPage
import com.lexx.presentation.ui.settings.SettingsPage
import com.lexx.telemetry.ui.plot.DayPlotPage
import com.lexx.telemetry.ui.plot.HourPlotPage
import com.lexx.telemetry.ui.plot.SixHoursPlotPage

@Composable
fun TelemetryAppContent(
    navigationType: NavigationType,
    currentContent: AppContentType,
    navigationItemContentList: List<NavigationItemContent>,
    onTabPressed: ((AppContentType) -> Unit),
    modifier: Modifier
) {
    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
            TelemetryAppNavigationRail(
                currentTab = currentContent,
                onTabPressed = onTabPressed,
                navigationItemContentList = navigationItemContentList
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            when (currentContent) {
                AppContentType.PLOT_HOUR_CONTENT_TYPE ->
                    HourPlotPage(
                        modifier = Modifier.weight(1f),
                    )

                AppContentType.PLOT_SIX_HOURS_CONTENT_TYPE ->
                    SixHoursPlotPage(
                        modifier = Modifier.weight(1f),
                    )

                AppContentType.PLOT_DAY_CONTENT_TYPE ->
                    DayPlotPage(
                        modifier = Modifier.weight(1f),
                    )

                AppContentType.SETTINGS_CONTENT_TYPE ->
                    SettingsPage(modifier = Modifier.weight(1f))

                AppContentType.SENSORS_CONTENT_TYPE ->
                    SensorsPage(modifier = Modifier.weight(1f))
            }

            AnimatedVisibility(
                visible = navigationType == NavigationType.BOTTOM_NAVIGATION
            ) {
                TelemetryAppBottomNavigationBar(
                    currentTab = currentContent,
                    onTabPressed = onTabPressed,
                    navigationItemContentList = navigationItemContentList,
                )
            }
        }
    }
}
