package com.adamian.learningzone.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.material3.Text

@Composable
fun HomeScreen() {
    // Sample data for the cards on the home screen
    val cardData = remember {
        List(8) { index -> "Item $index" }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(cardData) { item ->
                HomeCard(item)
            }
        }
    }
}

@Composable
fun HomeCard(content: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = content, style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}

