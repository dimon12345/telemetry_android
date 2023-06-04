package com.lexx.telemetry.ui.plot

import androidx.compose.ui.graphics.Color
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date


private val redColor = Color(1.0f, .0f, .0f)
private val blueColor = Color(.0f, 1.0f, .0f)
private val greenColor = Color(.0f, .0f, 1.0f)


data class PlotUiState (
    val plotInfo: PlotInfo = PlotInfo(),
    val xAxisLabels: List<String> = listOf(),
    val yAxisLabels: List<String> = listOf(),
)

data class PlotInfo (
    val values: Map<Int, PlotLineInfo> = mapOf(),
    val minValue: Float = Float.MAX_VALUE,
    val maxValue: Float = Float.MIN_VALUE,
    val minTimestamp: LocalDateTime = LocalDateTime.MAX,
    val maxTimestamp: LocalDateTime = LocalDateTime.MIN,
    val errorMessage: String = ""
)
data class PlotLineInfo (
    val Color: Color = greenColor,
    val points: List<Pair<Float, LocalDateTime>> = listOf()
)
