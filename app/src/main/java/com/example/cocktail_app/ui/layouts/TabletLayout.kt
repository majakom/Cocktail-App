package com.example.cocktail_app.ui.layouts

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.cocktail_app.data.Cocktail
import com.example.cocktail_app.ui.screens.CocktailDetailScreen
import com.example.cocktail_app.ui.screens.CocktailListScreen
import com.example.cocktail_app.ui.screens.PlaceholderScreen

@Composable
fun TabletLayout(
    modifier: Modifier = Modifier,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    var selectedCocktailStack by remember { mutableStateOf<List<Cocktail>>(emptyList()) }

    BackHandler(enabled = selectedCocktailStack.isNotEmpty()) {
        selectedCocktailStack = selectedCocktailStack.dropLast(1)
    }

    Row(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            CocktailListScreen(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected
            ) { cocktail ->
                selectedCocktailStack = selectedCocktailStack + cocktail
            }
        }
        Box(modifier = Modifier.weight(2f)) {
            selectedCocktailStack.lastOrNull()?.let {
                CocktailDetailScreen(it.name, it.ingredients.joinToString(", "), it.preparation, it.imageResId, true)
            } ?: PlaceholderScreen()
        }
    }
}