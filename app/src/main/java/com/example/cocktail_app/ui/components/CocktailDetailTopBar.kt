package com.example.cocktail_app.ui.components

import android.app.Activity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailDetailTopBar(
    name: String,
    imageResId: Int,
    isTablet: Boolean,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val imageHeight by animateDpAsState(
        targetValue = 250.dp * (1f - scrollBehavior.state.collapsedFraction),
        animationSpec = tween(durationMillis = 300),
        label = "imageHeight"
    )

    Box {
        CocktailImage(name, imageResId, imageHeight)
        LargeTopAppBar(
            title = {
                if (scrollBehavior.state.collapsedFraction >= 1f) {
                    Text(
                        text = "Szczegóły koktajlu",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            navigationIcon = {
                if (!isTablet)
                {
                    if (scrollBehavior.state.collapsedFraction >= 1f) {
                        IconButton(onClick = { activity?.finish() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    } else {
                        IconButton(onClick = { activity?.finish() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            },
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}