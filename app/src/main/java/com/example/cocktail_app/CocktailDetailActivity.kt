package com.example.cocktail_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class CocktailDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = intent.getStringExtra("name") ?: ""
        val ingredients = intent.getStringExtra("ingredients") ?: ""
        val preparation = intent.getStringExtra("preparation") ?: ""

        setContent {
            CocktailDetailScreen(name, ingredients, preparation)
        }
    }
}

@Composable
fun CocktailDetailScreen(name: String, ingredients: String, preparation: String) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        item { CocktailTitle(name) }
        item { SectionDivider() }
        item { IngredientsSection(ingredients) }
        item { SectionDivider() }
        item { PreparationSection(preparation) }
    }
}

@Composable
fun CocktailTitle(name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
    ) {
        Text(
            text = name,
            fontSize = 48.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun IngredientsSection(ingredients: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Składniki:",
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        ingredients.split(",").forEach { ingredient ->
            Text(
                text = "• ${ingredient.trim()}",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
fun PreparationSection(preparation: String) {
    Column {
        Text(
            text = "Sposób przygotowania:",
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = preparation,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Composable
fun SectionDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 16.dp),
        thickness = 2.dp
    )
}
