package com.lexx.presentation.ui.sensors

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lexx.presentation.R

@Composable
fun SensorsPage (
    sensorsViewModel: SensorsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState = sensorsViewModel.uiState.collectAsState().value
    LazyColumn(modifier.fillMaxWidth()) {
        if (uiState.errorText.isNotEmpty()) {
            item {
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = uiState.errorText,
                        color = Color.Red,
                        modifier = Modifier.weight(1.0f)
                    )
                    Button(
                        onClick = {
                            sensorsViewModel.observeSensorsInfo()
                        }

                    ) {
                        Text(stringResource(id = R.string.reload_title))
                    }
                }

            }
        }
        items(
            items = uiState.sensors,
            key = {it.nameId}
        ) {sensorInfo ->
            Text(sensorInfo.name, modifier)
        }
    }
}
