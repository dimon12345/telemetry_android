package com.lexx.telemetry.ui.plot

import java.sql.Time

data class PlotUiState (
    val data: Map<Int, List<DataPoint>> = mapOf()
)

data class DataPoint (
    val value: Float,
    val timestamp: Time
)
