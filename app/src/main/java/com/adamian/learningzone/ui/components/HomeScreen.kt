package com.adamian.learningzone.ui.components

import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.adamian.learningzone.R
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme

@Composable
fun HomeScreen() {
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(LearningZoneAppTheme.colorScheme.background)
        ) {
            // Background
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                // Top image
                Image(
                    painter = painterResource(id = R.drawable.top_background),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(y = (-260).dp)
                )

                // Bottom image
                Image(
                    painter = painterResource(id = R.drawable.bottom_background),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(y = 260.dp)
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .verticalScroll(rememberScrollState())

            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Καλημέρα",
                        style = LearningZoneAppTheme.typography.labelLarge
                    )
                    Text(
                        text = "Γιάννης Δαμιανάκης",
                        style = LearningZoneAppTheme.typography.titleLarge
                    )
                }
                HomeCard()

                Text(
                    text = "Επίπεδο",
                    style = LearningZoneAppTheme.typography.titleLarge
                )

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconLevelCard()
                    LevelCard()
                    IconLevelCard()
                }

                AverageCard()

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    SubscriptionCard()
                    StatsCard()
                }

            }
        }
    }
}

@Composable
fun HomeCard() {
    Card(
        colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.search_icon),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.width(18.dp))
            Column {
                Text(
                    text = "Ημερήσιο Κουίζ",
                    style = LearningZoneAppTheme.typography.titleLarge
                )
                Text(
                    text = "20 ερωτήσεις",
                    style = LearningZoneAppTheme.typography.labelLarge
                )
            }
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.right_arrow_icon),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .offset(x = 15.dp, y = 25.dp)
            )
        }
    }
}

@Composable
fun LevelCard() {
    Card(
        colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "1",
                style = LearningZoneAppTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "20 ερωτήσεις",
                style = LearningZoneAppTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun IconLevelCard() {
    Card(
        colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.lock_icon),
            tint = Color.Unspecified,
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
                .padding(16.dp)
        )
    }
}

@Composable
fun AverageCard() {
    Card(
        colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.average_icon),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.width(18.dp))
            Column {
                Text(
                    text = "Μέσος όρος",
                    style = LearningZoneAppTheme.typography.titleLarge
                )
                Text(
                    text = "Απαντήσεων",
                    style = LearningZoneAppTheme.typography.labelLarge
                )
            }
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.right_arrow_icon),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .offset(x = 15.dp, y = 25.dp)
            )
        }
    }
}

@Composable
fun SubscriptionCard() {
    Card(
        colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.subscription_icon),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(18.dp))
            Column {
                Text(
                    text = "Συμμετοχή",
                    style = LearningZoneAppTheme.typography.labelLarge
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.right_arrow_icon),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .offset(x = 15.dp, y = 25.dp)
                )
            }
        }
    }
}

@Composable
fun StatsCard() {
    Card(
        colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.stats_icon),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(18.dp))
            Column {
                Text(
                    text = "Στατιστικά",
                    style = LearningZoneAppTheme.typography.labelLarge
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.right_arrow_icon),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .offset(x = 15.dp, y = 25.dp)
                )
            }
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

