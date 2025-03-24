package com.example.cocktail_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = name, fontSize = 24.sp, modifier = Modifier.padding(bottom = 8.dp))
        Text(text = "Składniki: $ingredients", fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))
        Text(text = "Sposób przygotowania: $preparation", fontSize = 18.sp)
    }
}