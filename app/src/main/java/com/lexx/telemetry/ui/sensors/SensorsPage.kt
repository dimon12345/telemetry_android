package com.lexx.telemetry.ui.sensors

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lexx.telemetry.viewmodels.SensorsViewModel

@Composable
fun SensorsPage(
    sensorsViewModel: SensorsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState = sensorsViewModel.uiState.collectAsState().value
    LazyColumn(modifier.fillMaxWidth()) {
        items(
            items = uiState.sensors,
            key = {it.nameId}
        ) {sensorInfo ->
            Text(sensorInfo.name, modifier)
        }
    }
}
