package com.lexx.presentation.ui.sensors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lexx.presentation.R
import com.lexx.presentation.models.SensorUiInfo

@Composable
fun SensorsPage (
    sensorsViewModel: SensorsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState = sensorsViewModel.uiState.collectAsState().value
    if (uiState.showEditor) {
        SensorEditor(
            modifier = modifier,
            currentSensorInfo = uiState.selectedSensorInfo,
            onClose = {sensorsViewModel.onCloseEditor()},
            onColorChanged = {sensorsViewModel.onColorChanged(it)},
            onCheckedChange = {}
        )
    } else if(uiState.noSensorsError) {
        Text(
            text = stringResource(id = R.string.no_sensors_error),
            modifier.fillMaxSize()
        )
    } else {
        LazyColumn(
            modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White)
        ) {
            if (uiState.connectionError) {
                item {
                    Row(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.server_connect_error),
                            color = Color.Red,
                            modifier = Modifier.weight(1.0f)
                        )
                    }
                }
            } else {
                items(
                    items = uiState.sensors,
                    key = { it.nameId }
                ) { sensorInfo ->
                    SensorCard(
                        sensorInfo = sensorInfo,
                        onClick = {
                            sensorsViewModel.onSensorClick(it)
                        },
                        Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun SensorCard(
    sensorInfo: SensorUiInfo,
    onClick: (sensorInfo: SensorUiInfo) -> Unit,
    modifier: Modifier.Companion
) {
    Button(
        onClick = { onClick(sensorInfo) },
        modifier = modifier
            .fillMaxWidth()
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp),
            colors = CardDefaults.cardColors(containerColor=sensorInfo.color)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(vertical = 8.dp, horizontal = 12.dp)
            ) {
                Text(
                    text = sensorInfo.remoteName,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Text(
                    text = sensorInfo.description,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Row {
                    Text(stringResource(id = R.string.last_value), Modifier.wrapContentWidth())
                    Text(sensorInfo.lastValue)
                }

                Row {
                    Text(stringResource(id = R.string.last_seen), Modifier.wrapContentWidth())
                    Text(sensorInfo.lastTimestamp)
                }
            }
        }
    }
}
