package com.example.cocktail_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cocktail_app.ui.screens.MainScreen
import com.example.cocktail_app.ui.theme.Cocktail_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Cocktail_appTheme {
                MainScreen(onNavigateToDetail = { cocktail ->
                    val intent = Intent(this, CocktailDetailActivity::class.java).apply {
                        putExtra("name", cocktail.name)
                        putExtra("ingredients", cocktail.ingredients.joinToString(", "))
                        putExtra("preparation", cocktail.preparation)
                        putExtra("imageResId", cocktail.imageResId)
                    }
                    startActivity(intent)
                })
            }
        }
    }
}