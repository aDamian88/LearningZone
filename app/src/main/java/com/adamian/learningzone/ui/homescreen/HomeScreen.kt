package com.adamian.learningzone.ui.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.adamian.learningzone.R
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToChapters: (Int) -> Unit,
    navigateToQuiz: (Int) -> Unit
) {

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showStatsBottomSheet by remember { mutableStateOf(false) }
    var showSubscriptionBottomSheet by remember { mutableStateOf(false) }

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
                HomeCard(navigateToQuiz = navigateToQuiz)

                Text(
                    text = "Επίπεδο",
                    style = LearningZoneAppTheme.typography.titleLarge
                )

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconLevelCard()
                    LevelCard(navigateToChapters = navigateToChapters)
                    IconLevelCard()
                }

                AverageCard()

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    SubscriptionCard(onCardClick = { showSubscriptionBottomSheet = true })
                    StatsCard(onCardClick = { showStatsBottomSheet = true })
                }

            }
        }

        if (showSubscriptionBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showSubscriptionBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Button(onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showSubscriptionBottomSheet = false
                        }
                    }
                }) {
                    Text("Hide bottom sheet")
                }
            }
        }

        if (showStatsBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showStatsBottomSheet = false
                },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    Row {
                        SquareCard(
                            modifier = Modifier.padding(
                                top = 38.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            ),
                            topText = "Ολοκληρωμένα",
                            centerText = "Κουίζ",
                            bottomText = "223",
                            backgroundColor = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.primary)
                        )
                        SquareCard(
                            modifier = Modifier.padding(16.dp),
                            topText = "Ολοκληρωμένα",
                            centerText = "Κουίζ",
                            bottomText = "90",
                            backgroundColor = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.secondary)
                        )
                    }
                    Row {
                        SquareCard(
                            modifier = Modifier.padding(
                                top = 38.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            ),
                            topText = "Ολοκληρωμένα",
                            centerText = "Κουίζ",
                            bottomText = "123",
                            backgroundColor = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.tertiary)
                        )
                        SquareCard(
                            modifier = Modifier.padding(16.dp),
                            topText = "Ολοκληρωμένα",
                            centerText = "Κουίζ",
                            bottomText = "123",
                            backgroundColor = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.quaternary)
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun HomeCard(
    navigateToQuiz: (Int) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        onClick = { navigateToQuiz(0) }
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
fun LevelCard(
    navigateToChapters: (Int) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        onClick = { navigateToChapters(0) }
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
fun SquareCard(
    modifier: Modifier = Modifier,
    topText: String,
    centerText: String,
    bottomText: String,
    backgroundColor: CardColors
) {
    Card(
        colors = backgroundColor,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = topText,
                style = LearningZoneAppTheme.typography.labelLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )
            Text(
                text = centerText,
                style = LearningZoneAppTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )
            Text(
                text = bottomText,
                style = LearningZoneAppTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
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
fun SubscriptionCard(onCardClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(16.dp)
            .clickable { onCardClick() },
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
fun StatsCard(onCardClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(16.dp)
            .clickable { onCardClick() },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Text(
            text = "This is a BottomSheet",
            style = LearningZoneAppTheme.typography.labelLarge
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
    }
}

