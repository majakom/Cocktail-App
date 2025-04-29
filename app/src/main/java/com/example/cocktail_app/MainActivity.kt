package com.example.cocktail_app

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.sp
import com.example.cocktail_app.data.Cocktail
import com.example.cocktail_app.data.cocktails
import com.example.cocktail_app.ui.theme.Cocktail_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Cocktail_appTheme {
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                var selectedTab by remember { mutableStateOf(0) }  // <- NEW!

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerContent { selectedItem ->
                            selectedTab = when (selectedItem) {
                                "home" -> 0
                                "alcoholic" -> 1
                                "nonalcoholic" -> 2
                                else -> 0
                            }
                            scope.launch { drawerState.close() }
                        }
                    }
                ) {
                    Scaffold(
                        topBar = {
                            CocktailTopBar(
                                title = "Koktajle",
                                onMenuClick = { scope.launch { drawerState.open() } }
                            )
                        }
                    ) { innerPadding ->
                        val isTablet = isTablet()
                        if (isTablet) {
                            TabletLayout(
                                modifier = Modifier.padding(innerPadding),
                                selectedTab = selectedTab,
                                onTabSelected = { selectedTab = it }
                            )
                        } else {
                            CocktailListScreen(
                                modifier = Modifier.padding(innerPadding),
                                selectedTab = selectedTab,
                                onTabSelected = { selectedTab = it }
                            ) { cocktail ->
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
        }
    }

    private fun isTablet(): Boolean {
        return resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }
}

@Composable
fun DrawerContent(onItemSelected: (String) -> Unit) {
    ModalDrawerSheet(
        modifier = Modifier
            .fillMaxHeight()
            .width(250.dp),
        drawerContainerColor = MaterialTheme.colorScheme.primary,
        drawerContentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Menu",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 2.dp
            )
            Text(
                text = "Strona główna",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemSelected("home") }
                    .padding(8.dp)
            )
            Text(
                text = "Drinki z alkoholem",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemSelected("alcoholic") }
                    .padding(8.dp)
            )
            Text(
                text = "Drinki bezalkoholowe",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemSelected("nonalcoholic") }
                    .padding(8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    onMenuClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            when {
                onBackClick != null -> {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Cofnij",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                onMenuClick != null -> {
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    )
}


@Composable
fun TabletLayout(
    modifier: Modifier = Modifier,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    var selectedCocktailStack by remember { mutableStateOf<List<Cocktail>>(emptyList()) }

    BackHandler(enabled = selectedCocktailStack.isNotEmpty()) {
        selectedCocktailStack = selectedCocktailStack.dropLast(1)
    }

    Row(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            CocktailListScreen(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected
            ) { cocktail ->
                selectedCocktailStack = selectedCocktailStack + cocktail
            }
        }
        Box(modifier = Modifier.weight(2f)) {
            selectedCocktailStack.lastOrNull()?.let {
                CocktailDetailScreen(it.name, it.ingredients.joinToString(", "), it.preparation, it.imageResId, true)
            } ?: PlaceholderScreen()
        }
    }
}

@Composable
fun CocktailListScreen(
    modifier: Modifier = Modifier,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onCocktailClick: (Cocktail) -> Unit
) {
    val tabs = listOf("Strona tytułowa", "Drinki z alkoholem", "Drinki bezalkoholowe")

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { onTabSelected(index) },
                    text = { Text(title) }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(selectedTab) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume()
                        if (dragAmount > 75) {
                            onTabSelected((selectedTab - 1).coerceAtLeast(0))
                        } else if (dragAmount < -75) {
                            onTabSelected((selectedTab + 1).coerceAtMost(tabs.lastIndex))
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            when (selectedTab) {
                0 -> {
                    Text(
                        text = "Witaj w aplikacji koktajlowej!",
                        fontSize = 24.sp
                    )
                }
                1 -> {
                    val alcoholicDrinks = cocktails.filter { it.isAlcoholic }
                    CocktailGrid(alcoholicDrinks, onCocktailClick)
                }
                2 -> {
                    val nonAlcoholicDrinks = cocktails.filter { !it.isAlcoholic }
                    CocktailGrid(nonAlcoholicDrinks, onCocktailClick)
                }
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