package com.example.cocktail_app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.cocktail_app.ui.components.CocktailDetailTopBar
import com.example.cocktail_app.ui.components.CocktailTitle
import com.example.cocktail_app.ui.components.CountdownTimer
import com.example.cocktail_app.ui.components.IngredientsSection
import com.example.cocktail_app.ui.components.PreparationSection
import com.example.cocktail_app.ui.components.SectionDivider
import com.example.cocktail_app.ui.components.SendSmsFab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailDetailScreen(name: String, ingredients: String, preparation: String, imageResId: Int, isTablet: Boolean) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CocktailDetailTopBar(name, imageResId, isTablet, scrollBehavior)

        },
        floatingActionButton = {
            SendSmsFab {
                Toast
                    .makeText(context, "SMS: Składniki - $ingredients", Toast.LENGTH_LONG)
                    .show()
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            item { CocktailTitle(name) }
            item { SectionDivider() }
            item { IngredientsSection(ingredients) }
            item { SectionDivider() }
            item { PreparationSection(preparation) }
            item { SectionDivider() }
            item { CountdownTimer() }
        }
    }

}