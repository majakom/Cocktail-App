package com.example.cocktail_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cocktail_app.ui.screens.CocktailDetailScreen
import com.example.cocktail_app.ui.theme.Cocktail_appTheme

class CocktailDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = intent.getStringExtra("name") ?: ""
        val ingredients = intent.getStringExtra("ingredients") ?: ""
        val preparation = intent.getStringExtra("preparation") ?: ""
        val imageResId = intent.getIntExtra("imageResId", 0)
        setContent {
            Cocktail_appTheme {
                CocktailDetailScreen(name, ingredients, preparation, imageResId, false)
            }
        }
    }
}





