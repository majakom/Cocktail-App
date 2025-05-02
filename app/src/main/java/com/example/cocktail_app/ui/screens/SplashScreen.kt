package com.example.cocktail_app.ui.screens

import android.animation.ObjectAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.cocktail_app.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onAnimationFinished: () -> Unit) {
    val imageViewState = remember { mutableStateOf<ImageView?>(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = {
                ImageView(it).apply {
                    setImageResource(R.drawable.cocktail_app_icon)
                    scaleX = 0f
                    scaleY = 0f
                    imageViewState.value = this
                }
            }
        )
    }

    LaunchedEffect(Unit) {
        val view = imageViewState.value
        if (view != null) {
            val animatorX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 0.5f).apply {
                duration = 1000
                interpolator = AccelerateDecelerateInterpolator()
            }
            val animatorY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 0.5f).apply {
                duration = 1000
                interpolator = AccelerateDecelerateInterpolator()
            }

            animatorX.start()
            animatorY.start()

            delay(1200)
            onAnimationFinished()
        }
    }
}