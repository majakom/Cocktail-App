package com.example.cocktail_app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CountdownTimer() {
    var inputTime by remember { mutableStateOf("60") }
    var timeLeft by remember { mutableIntStateOf(60) }
    var isRunning by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimerInputField(inputTime = inputTime, onInputChange = { newTime ->
            inputTime = newTime
            timeLeft = newTime.toInt()
        })

        TimerDisplay(timeLeft = timeLeft)

        TimerControls(
            isRunning = isRunning,
            onStart = {
                if (!isRunning) {
                    isRunning = true
                    coroutineScope.launch {
                        while (isRunning && timeLeft > 0) {
                            delay(1000L)
                            if (isRunning && timeLeft > 0) {
                                timeLeft--
                            } else {
                                isRunning = false
                            }
                        }
                    }
                }
            },
            onStop = { isRunning = false },
            onReset = {
                isRunning = false
                timeLeft = inputTime.toIntOrNull() ?: 60
            }
        )
    }
}

@Composable
fun TimerInputField(inputTime: String, onInputChange: (String) -> Unit) {
    Row(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(text = "Ustaw czas: ", fontSize = 28.sp)
        BasicTextField(
            value = inputTime,
            onValueChange = { newValue ->
                onInputChange(newValue.filter { it.isDigit() }.take(3).ifEmpty { "0" })
            },
            modifier = Modifier.width(48.dp),
            textStyle = TextStyle(fontSize = 28.sp, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onBackground)
        )
        Text(text = " s", fontSize = 28.sp)
    }
}

@Composable
fun TimerDisplay(timeLeft: Int) {
    Text(
        text = "\u23F1 $timeLeft s",
        fontSize = 28.sp,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
fun TimerControls(
    isRunning: Boolean,
    onStart: () -> Unit,
    onStop: () -> Unit,
    onReset: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(onClick = onStart) {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Start")
        }

        Button(onClick = onStop) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Stop")
        }

        Button(onClick = onReset) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset")
        }
    }
}
