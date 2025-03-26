package com.example.cocktail_app

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocktail_app.data.Cocktail
import com.example.cocktail_app.data.cocktails

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isTablet = isTablet()

            if (isTablet) {
                TabletLayout();
            } else {
                CocktailListScreen { cocktail ->
                    val intent = Intent(this, CocktailDetailActivity::class.java).apply {
                        putExtra("name", cocktail.name)
                        putExtra("ingredients", cocktail.ingredients.joinToString(", "))
                        putExtra("preparation", cocktail.preparation)
                    }
                    startActivity(intent)
                }
            }
        }
    }

    private fun isTablet(): Boolean {
        return resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }
}

@Composable
fun TabletLayout() {
    var selectedCocktailStack by remember { mutableStateOf<List<Cocktail>>(emptyList()) }

    BackHandler(enabled = selectedCocktailStack.isNotEmpty()) {
        selectedCocktailStack = selectedCocktailStack.dropLast(1)
    }

    Row(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            CocktailListScreen { cocktail ->
                selectedCocktailStack = selectedCocktailStack + cocktail
            }
        }
        Box(modifier = Modifier.weight(2f)) {
            selectedCocktailStack.lastOrNull()?.let {
                CocktailDetailScreen(it.name, it.ingredients.joinToString(", "), it.preparation)
            } ?: PlaceholderScreen()
        }
    }
}

@Composable
fun CocktailListScreen(onCocktailClick: (Cocktail) -> Unit) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(cocktails) { cocktail ->
            CocktailListItem(cocktail, onCocktailClick)
        }
    }
}

@Composable
fun CocktailListItem(cocktail: Cocktail, onCocktailClick: (Cocktail) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCocktailClick(cocktail) }
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = cocktail.name,
            fontSize = 20.sp
        )
    }
}

@Composable
fun PlaceholderScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text(text = "Wybierz koktajl", fontSize = 24.sp)
    }
}
