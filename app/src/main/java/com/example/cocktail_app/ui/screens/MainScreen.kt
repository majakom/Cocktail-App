package com.example.cocktail_app.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.cocktail_app.data.Cocktail
import com.example.cocktail_app.ui.components.CocktailTopBar
import com.example.cocktail_app.ui.layouts.TabletLayout
import com.example.cocktail_app.ui.navigation.DrawerContent
import kotlinx.coroutines.launch

@Composable
fun MainScreen(onNavigateToDetail: (Cocktail) -> Unit) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedTab by remember { mutableStateOf(0) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent { selectedItem ->
                selectedTab = when (selectedItem) {
                    "home" -> 0
                    "alcoholic" -> 1
                    "nonalcoholic" -> 2
                    else -> 0
                }
                scope.launch { drawerState.close() }
            }
        }
    ) {
        Scaffold(
            topBar = {
                CocktailTopBar(
                    title = "Koktajle",
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            }
        ) { innerPadding ->
            val isTablet = isTablet()
            LayoutSwitcher(
                isTablet = isTablet,
                innerPadding = innerPadding,
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                onNavigateToDetail = onNavigateToDetail
            )
        }
    }
}

@Composable
fun LayoutSwitcher(
    isTablet: Boolean,
    innerPadding: PaddingValues,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onNavigateToDetail: (Cocktail) -> Unit
) {
    if (isTablet) {
        TabletLayout(
            modifier = Modifier.padding(innerPadding),
            selectedTab = selectedTab,
            onTabSelected = onTabSelected
        )
    } else {
        CocktailListScreen(
            modifier = Modifier.padding(innerPadding),
            selectedTab = selectedTab,
            onTabSelected = onTabSelected,
            onCocktailClick = onNavigateToDetail
        )
    }
}

@Composable
fun isTablet(): Boolean {
    val context = LocalContext.current
    return (context.resources.configuration.screenLayout and
            Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
}