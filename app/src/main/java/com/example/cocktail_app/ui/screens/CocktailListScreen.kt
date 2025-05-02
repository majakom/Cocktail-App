package com.example.cocktail_app.ui.screens

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.example.cocktail_app.data.Cocktail
import com.example.cocktail_app.data.cocktails
import com.example.cocktail_app.ui.components.CocktailGrid

@Composable
fun CocktailListScreen(
    modifier: Modifier = Modifier,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onCocktailClick: (Cocktail) -> Unit
) {
    val tabs = listOf("Strona tytułowa", "Drinki z alkoholem", "Drinki bezalkoholowe")

    Column(modifier = modifier.fillMaxSize()) {
        TabBar(selectedTab = selectedTab, onTabSelected = onTabSelected, tabs = tabs)
        TabContent(
            selectedTab = selectedTab,
            onTabSelected = onTabSelected,
            onCocktailClick = onCocktailClick,
            tabs = tabs
        )
    }
}

@Composable
fun TabBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    tabs: List<String>
) {
    TabRow(selectedTabIndex = selectedTab) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                text = { Text(title) }
            )
        }
    }
}

@Composable
fun TabContent(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onCocktailClick: (Cocktail) -> Unit,
    tabs: List<String>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(selectedTab) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    if (dragAmount > 75) {
                        onTabSelected((selectedTab - 1).coerceAtLeast(0))
                    } else if (dragAmount < -75) {
                        onTabSelected((selectedTab + 1).coerceAtMost(tabs.lastIndex))
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        when (selectedTab) {
            0 -> {
                HomePageWithShakersScreen()
            }
            1 -> {
                val alcoholicDrinks = cocktails.filter { it.isAlcoholic }
                CocktailGrid(alcoholicDrinks, onCocktailClick)
            }
            2 -> {
                val nonAlcoholicDrinks = cocktails.filter { !it.isAlcoholic }
                CocktailGrid(nonAlcoholicDrinks, onCocktailClick)
            }
        }
    }
}
