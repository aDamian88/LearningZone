package com.adamian.learningzone.ui.homescreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adamian.learningzone.R
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToChapters: (Int) -> Unit,
    viewModel: HomeScreenVM = hiltViewModel()
) {

    val sheetState = rememberModalBottomSheetState()
    var showStatsBottomSheet by remember { mutableStateOf(false) }
    var showSubscriptionBottomSheet by remember { mutableStateOf(false) }
    val appStats by viewModel.appStats.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadAppStats()
    }

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
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(0.8f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Καλώς ήρθες",
                        style = LearningZoneAppTheme.typography.labelLarge
                    )
                    Text(
                        text = "Καλή μάθηση!",
                        style = LearningZoneAppTheme.typography.titleLarge
                    )
                }

                appStats?.let {
                    AverageFrame(
                        modifier = Modifier
                            .weight(1.5f)
                            .fillMaxWidth(),
                        completion = it.completionPercentage
                    )
                }

                HomeCard(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    navigateToChapters = navigateToChapters
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bottom_sheet_background),
                        contentDescription = null,
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Ρυθμίσεις",
                            style = LearningZoneAppTheme.typography.titleLarge
                        )

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
                            Text(
                                modifier = Modifier
                                    .padding(16.dp),
                                text = "Ξεκλείδωσε όλες τις δυνατότητες της εφαρμογής για ένα χρόνο! " +
                                        "Εκτός από το επίπεδο 1 θα έχεις διαθέσιμα τα επίπεδα 2 και 3 " +
                                        "με περισσότερες ερωτήσεις για καλύτερη προετοιμασία!",
                                style = LearningZoneAppTheme.typography.labelLarge
                            )
                        }

                        Card(
                            colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.quaternary),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(16.dp),
                                text = "Διαθέσιμο σε επόμενη έκδοση",
                                style = LearningZoneAppTheme.typography.labelLarge
                            )
                        }
                    }
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bottom_sheet_background),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Στατιστικά",
                            style = LearningZoneAppTheme.typography.titleLarge
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 48.dp)
                        ) {
                            SquareCard(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(
                                        top = 48.dp,
                                        start = 8.dp,
                                        end = 8.dp,
                                        bottom = 8.dp
                                    ),
                                topText = "Ερωτήσεις",
                                centerText = "Σύνολο",
                                bottomText = appStats?.totalQuestions.toString(),
                                backgroundColor = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.tertiary)
                            )
                            SquareCard(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp),
                                topText = "Απαντήσεις",
                                centerText = "Σύνολο",
                                bottomText = appStats?.totalAnswers.toString(),
                                backgroundColor = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.primary)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            SquareCard(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(
                                        top = 48.dp,
                                        start = 8.dp,
                                        end = 8.dp,
                                        bottom = 8.dp
                                    ),
                                topText = "Απαντήσεις",
                                centerText = "Σωστές",
                                bottomText = appStats?.totalCorrectAnswers.toString(),
                                backgroundColor = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.quaternary)
                            )
                            SquareCard(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp),
                                topText = "Απαντήσεις",
                                centerText = "Λάθος",
                                bottomText = appStats?.totalWrongAnswers.toString(),
                                backgroundColor = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.secondary)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuizProgressIndicator(
    progress: Float, // progress value between 0f and 1f
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    strokeWidth: Dp = 8.dp,
    correctAnswersPercentage: Int // percentage value (0-100)
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
    ) {
        // CircularProgressIndicator
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            color = LearningZoneAppTheme.colorScheme.primary,
            strokeWidth = strokeWidth,
        )
        // Percentage Text
        Text(
            text = "${correctAnswersPercentage}%",
            style = LearningZoneAppTheme.typography.titleLarge,
            color = LearningZoneAppTheme.colorScheme.primary
        )
    }
}

@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Green,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        ), label = ""
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        modifier = Modifier.size(radius * 2)
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawArc(
                color = Color.LightGray, // You can customize this
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            )
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * curPercentage.value,
                useCenter = false,
                style = Stroke(
                    strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }
        Text(
            text = (curPercentage.value * number).toInt().toString(),
            color = Color.Black,
            modifier = Modifier.align(Alignment.Center),
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
fun HomeCard(
    modifier: Modifier,
    navigateToChapters: (Int) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        onClick = { navigateToChapters(0) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
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

            Spacer(modifier = Modifier.width(36.dp))

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.right_arrow_icon),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
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
    backgroundColor: CardColors = CardDefaults.cardColors() // Added a default value
) {
    Card(
        colors = backgroundColor,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(
                8.dp,
                Alignment.CenterVertically
            ), // Spaces out children
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = topText,
                style = LearningZoneAppTheme.typography.labelLarge
            )
            Text(
                text = centerText,
                style = LearningZoneAppTheme.typography.titleLarge
            )
            Text(
                text = bottomText,
                style = LearningZoneAppTheme.typography.titleLarge
            )
        }
    }
}


@Composable
fun AverageFrame(
    modifier: Modifier = Modifier,
    completion: Double
) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = LearningZoneAppTheme.colorScheme.onBackground,
            )
            .background(
                color = LearningZoneAppTheme.colorScheme.background,
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
//                QuizProgressIndicator(
//                    progress = completion.toFloat(),
//                    correctAnswersPercentage = (completion * 100).toInt()
//                )
                CircularProgressBar(
                    percentage = 0.8f,
                    number = 100
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
            }

            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = "Αρχική έκδοση της εφαργμογής περιέχει κάποιες ερωτήσεις που " +
                        "έχουν μπει στις πανελλήνιες εξετάσεις. Αφορούν " +
                        "όλη την ύλη του βιβλίου και θα ενημερωθούν με περισσότερες.",
                style = LearningZoneAppTheme.typography.labelLarge
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
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.subscription_icon),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ρυθμίσεις",
                style = LearningZoneAppTheme.typography.labelLarge
            )
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
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.stats_icon),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Στατιστικά",
                style = LearningZoneAppTheme.typography.labelLarge
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
    }
}

