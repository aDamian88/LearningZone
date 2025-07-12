package com.adamian.learningzone.ui.homescreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamian.learningzone.R
import com.adamian.learningzone.domain.model.AppStats
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme.neonColor

@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Int,
    radius: Dp = 30.dp,
    color: Color = LearningZoneAppTheme.colorScheme.primary,
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
            color = LearningZoneAppTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.Center),
            style = LearningZoneAppTheme.typography.bodyBold
        )
    }
}

@Composable
fun HomeCard(
    modifier: Modifier,
    navigateToChapters: (Int) -> Unit
) {

    Card(
        colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = neonColor.copy(alpha = 0.8f),
                spotColor = neonColor.copy(alpha = 0.8f)
            ),
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
                    style = LearningZoneAppTheme.typography.titleLarge,
                    color = LearningZoneAppTheme.colorScheme.onBackground,
                )
                Text(
                    text = "20 ερωτήσεις",
                    style = LearningZoneAppTheme.typography.labelLarge,
                    color = LearningZoneAppTheme.colorScheme.onBackground
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
        modifier = modifier
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = neonColor.copy(alpha = 0.8f),
                spotColor = neonColor.copy(alpha = 0.8f)
            ),
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
                style = LearningZoneAppTheme.typography.labelLarge,
                color = LearningZoneAppTheme.colorScheme.onBackground
            )
            Text(
                text = centerText,
                style = LearningZoneAppTheme.typography.titleLarge,
                color = LearningZoneAppTheme.colorScheme.onBackground
            )
            Text(
                text = bottomText,
                style = LearningZoneAppTheme.typography.titleLarge,
                color = LearningZoneAppTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun AverageFrame(
    modifier: Modifier = Modifier.background(
        color = LearningZoneAppTheme.colorScheme.surface,
    ),
    completion: Double
) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(
                color = LearningZoneAppTheme.colorScheme.surface,
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
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Level"
                )
                Spacer(modifier = Modifier.width(18.dp))

                Text(
                    text = "Επίπεδο 1",
                    style = LearningZoneAppTheme.typography.titleLarge,
                    color = LearningZoneAppTheme.colorScheme.onBackground,
                )
                Spacer(modifier = Modifier.width(18.dp))

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Theory"
                )
                Spacer(modifier = Modifier.width(18.dp))

                Text(
                    text = "Θεωρία",
                    style = LearningZoneAppTheme.typography.titleNormal,
                    color = LearningZoneAppTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(18.dp))

                CircularProgressBar(
                    percentage = completion.toFloat(),
                    number = 100 // todo magic number
                )
            }

            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = "Πρώτο πέρασμα της ύλης, αποκτάς βασικές γνώσεις για το μάθημα.",
                style = LearningZoneAppTheme.typography.labelLarge,
                color = LearningZoneAppTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun SubscriptionCard(onCardClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(16.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = neonColor.copy(alpha = 0.8f),
                spotColor = neonColor.copy(alpha = 0.8f)
            )
            .clickable { onCardClick() }
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
                style = LearningZoneAppTheme.typography.labelLarge,
                color = LearningZoneAppTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun StatsCard(onCardClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(16.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = neonColor.copy(alpha = 0.8f),
                spotColor = neonColor.copy(alpha = 0.8f)
            )
            .clickable { onCardClick() }
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
                style = LearningZoneAppTheme.typography.labelLarge,
                color = LearningZoneAppTheme.colorScheme.onBackground
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionBottomSheet(onDismiss: () -> Unit, sheetState: SheetState) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = LearningZoneAppTheme.colorScheme.background,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.bottom_sheet_background),
                contentDescription = null
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
                    style = LearningZoneAppTheme.typography.titleLarge,
                    color = LearningZoneAppTheme.colorScheme.onBackground
                )

                Card(
                    colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(20.dp),
                            ambientColor = neonColor.copy(alpha = 0.8f),
                            spotColor = neonColor.copy(alpha = 0.8f)
                        )
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = "Ξεκλείδωσε όλες τις δυνατότητες της εφαρμογής για ένα χρόνο! " +
                                "Εκτός από το επίπεδο 1 θα έχεις διαθέσιμα τα επίπεδα 2 και 3 " +
                                "με περισσότερες ερωτήσεις για καλύτερη προετοιμασία!",
                        style = LearningZoneAppTheme.typography.labelLarge,
                        color = LearningZoneAppTheme.colorScheme.onBackground
                    )
                }

                Card(
                    colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.quaternary),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(20.dp),
                            ambientColor = neonColor.copy(alpha = 0.8f),
                            spotColor = neonColor.copy(alpha = 0.8f)
                        )
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = "Διαθέσιμο σε επόμενη έκδοση",
                        style = LearningZoneAppTheme.typography.labelLarge,
                        color = LearningZoneAppTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsBottomSheet(
    appStats: AppStats?,
    onDismiss: () -> Unit,
    sheetState: SheetState
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = LearningZoneAppTheme.colorScheme.background,
        ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.bottom_sheet_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
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
                    style = LearningZoneAppTheme.typography.titleLarge,
                    color = LearningZoneAppTheme.colorScheme.onBackground
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

