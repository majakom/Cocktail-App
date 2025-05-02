package com.example.cocktail_app.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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