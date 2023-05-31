package com.lexx.telemetry.ui.plot

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lexx.telemetry.viewmodels.PlotViewModel

@Composable
fun PlotPage (
    plotViewModel: PlotViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState = plotViewModel.uiState.collectAsState().value
    Text(
        text= "Plot"
    )
}
