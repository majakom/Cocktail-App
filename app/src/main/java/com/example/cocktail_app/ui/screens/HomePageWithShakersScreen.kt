package com.example.cocktail_app.ui.screens

import android.animation.ObjectAnimator
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.animation.CycleInterpolator
import android.widget.ImageView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.cocktail_app.R
import kotlin.math.sqrt

@Composable
fun HomePageWithShakersScreen() {
    val shakeTrigger = remember { mutableStateOf(false) }

    // Sensor logic
    ShakeSensor { shakeTrigger.value = true }

    val shaker1 = remember { mutableStateOf<ImageView?>(null) }
    val shaker2 = remember { mutableStateOf<ImageView?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Witaj w aplikacji koktajlowej!",
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.Center)
        )

        ShakerView(
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.TopEnd)
                .padding(16.dp),
            rotation = -45f,
            imageViewState = shaker1
        )

        ShakerView(
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.BottomStart)
                .padding(16.dp),
            rotation = 45f,
            imageViewState = shaker2
        )

        AnimateShake(shakeTrigger.value, listOf(shaker1.value, shaker2.value)) {
            shakeTrigger.value = false
        }
    }
}

@Composable
fun ShakeSensor(onShake: () -> Unit) {
    val context = LocalContext.current

    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val shakeListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    val x = it.values[0]
                    val y = it.values[1]
                    val magnitude = sqrt(x * x + y * y)
                    if (magnitude > 10) onShake()
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(shakeListener, accelerometer, SensorManager.SENSOR_DELAY_UI)
        onDispose {
            sensorManager.unregisterListener(shakeListener)
        }
    }
}

@Composable
fun ShakerView(
    modifier: Modifier,
    rotation: Float,
    imageViewState: MutableState<ImageView?>
) {
    AndroidView(
        factory = {
            ImageView(it).apply {
                setImageResource(R.drawable.shaker)
                this.rotation = rotation
                imageViewState.value = this
            }
        },
        modifier = modifier
    )
}

@Composable
fun AnimateShake(trigger: Boolean, shakers: List<ImageView?>, onComplete: () -> Unit) {
    LaunchedEffect(trigger) {
        if (trigger) {
            shakers.forEach { shaker ->
                shaker?.let {
                    ObjectAnimator.ofFloat(it, "translationY", 0f, 25f).apply {
                        duration = 500
                        interpolator = CycleInterpolator(5f)
                    }.start()
                }
            }
            onComplete()
        }
    }
}
