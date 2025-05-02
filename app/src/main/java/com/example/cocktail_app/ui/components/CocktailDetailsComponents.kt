package com.example.cocktail_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
fun SectionDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 16.dp),
        thickness = 2.dp
    )
}