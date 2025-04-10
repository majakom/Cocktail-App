package com.example.cocktail_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        item { SectionDivider() }
        item { CountdownTimer() }
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
fun CountdownTimer() {
    var inputTime by remember { mutableStateOf("60") }
    var timeLeft by remember { mutableIntStateOf(60) }
    var isRunning by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Text(text = "Ustaw czas: ", fontSize = 28.sp)
            BasicTextField(
                value = inputTime,
                onValueChange = {
                    inputTime = it.filter { c -> c.isDigit() }.take(3).ifEmpty { "0" }.toInt().toString()
                    timeLeft = inputTime.toInt()
                },
                modifier = Modifier.width(48.dp),
                textStyle = TextStyle(fontSize = 28.sp, textAlign = TextAlign.Center)
            )
            Text(text = " s", fontSize = 28.sp)
        }

        Text(
            text = "\u23F1 $timeLeft s",
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = {
                if (!isRunning) {
                    isRunning = true
                    coroutineScope.launch {
                        while (isRunning && timeLeft > 0) {
                            delay(1000L)
                            if (isRunning && timeLeft > 0) {
                                timeLeft--
                            } else {
                                isRunning = false
                            }
                        }
                    }
                }
            }) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Start")
            }

            Button(onClick = { isRunning = false }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Stop")
            }

            Button(onClick = {
                isRunning = false
                timeLeft = inputTime.toIntOrNull() ?: 60
            }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset")
            }
        }
    }
}

@Composable
fun SectionDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 16.dp),
        thickness = 2.dp
    )
}



