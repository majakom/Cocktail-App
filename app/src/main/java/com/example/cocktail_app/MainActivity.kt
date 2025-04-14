package com.example.cocktail_app

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocktail_app.data.Cocktail
import com.example.cocktail_app.data.cocktails
import com.example.cocktail_app.ui.theme.Cocktail_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Cocktail_appTheme {
                val isTablet = isTablet()

                if (isTablet) {
                    TabletLayout();
                } else {
                    CocktailListScreen { cocktail ->
                        val intent = Intent(this, CocktailDetailActivity::class.java).apply {
                            putExtra("name", cocktail.name)
                            putExtra("ingredients", cocktail.ingredients.joinToString(", "))
                            putExtra("preparation", cocktail.preparation)
                            putExtra("imageResId", cocktail.imageResId)
                        }
                        startActivity(intent)
                    }
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
                CocktailDetailScreen(it.name, it.ingredients.joinToString(", "), it.preparation, it.imageResId)
            } ?: PlaceholderScreen()
        }
    }
}

@Composable
fun CocktailListScreen(onCocktailClick: (Cocktail) -> Unit) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Strona tytułowa", "Drinki z alkoholem", "Drinki bezalkoholowe")

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        when (selectedTab) {
            0 -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Witaj w aplikacji koktajlowej!", fontSize = 24.sp)
                }
            }
            1 -> {
                val alcoholicDrinks = cocktails.filter { it.isAlcoholic }
                CocktailGrid(alcoholicDrinks, onCocktailClick)
            }
            2 -> {
                // Drinki bezalkoholowe
                val nonAlcoholicDrinks = cocktails.filter { !it.isAlcoholic }
                CocktailGrid(nonAlcoholicDrinks, onCocktailClick)
            }
        }
    }
}

@Composable
fun CocktailGrid(cocktails: List<Cocktail>, onCocktailClick: (Cocktail) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(cocktails) { cocktail ->
            CocktailGridItem(cocktail, onCocktailClick)
        }
    }
}

@Composable
fun CocktailGridItem(cocktail: Cocktail, onCocktailClick: (Cocktail) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCocktailClick(cocktail) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = cocktail.imageResId),
                contentDescription = cocktail.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = cocktail.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PlaceholderScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text(text = "Wybierz koktajl", fontSize = 24.sp)
    }
}
