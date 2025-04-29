package com.example.cocktail_app

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocktail_app.ui.theme.Cocktail_appTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailDetailTopBar(
    name: String,
    imageResId: Int,
    isTablet: Boolean,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val imageHeight by animateDpAsState(
        targetValue = 250.dp * (1f - scrollBehavior.state.collapsedFraction),
        animationSpec = tween(durationMillis = 300),
        label = "imageHeight"
    )

    Box {
        CocktailImage(name, imageResId, imageHeight)
        LargeTopAppBar(
            title = {
                if (scrollBehavior.state.collapsedFraction >= 1f) {
                    Text(
                        text = "Szczegóły koktajlu",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            navigationIcon = {
                if (!isTablet)
                {
                    if (scrollBehavior.state.collapsedFraction >= 1f) {
                        IconButton(onClick = { activity?.finish() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    } else {
                        IconButton(onClick = { activity?.finish() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            },
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
fun SendSmsFab(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = "Wyślij SMS"
        )
    }
}

@Composable
fun CocktailImage(name: String, imageResId: Int, imageHeight: Dp) {
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = name,
        modifier = Modifier
            .fillMaxWidth()
            .height(imageHeight)
            .padding(bottom = 16.dp),
        contentScale = ContentScale.Fit,
    )
}

@Composable
fun CocktailTitle(name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = name,
            fontSize = 48.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            lineHeight = 52.sp,
            softWrap = true,
            maxLines = 2,
            overflow = TextOverflow.Clip
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
                textStyle = TextStyle(fontSize = 28.sp, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onBackground)
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



