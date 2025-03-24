package com.example.cocktail_app
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocktail_app.data.Cocktail
import com.example.cocktail_app.data.cocktails

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
