package com.adamian.learningzone.ui.homescreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamian.learningzone.R
import com.adamian.learningzone.domain.model.AppStats
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme.neonColor
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty

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
                imageVector = ImageVector.vectorResource(id = R.drawable.rocketdoodle),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.width(18.dp))
            Column {
                Text(
                    text = "Κεφάλαια",
                    style = LearningZoneAppTheme.typography.titleLarge,
                    color = LearningZoneAppTheme.colorScheme.onBackground,
                )
                Text(
                    text = "Η θεωρία της ύλης σε κουίζ",
                    style = LearningZoneAppTheme.typography.labelLarge,
                    color = LearningZoneAppTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.width(36.dp))
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
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = topText,
                style = LearningZoneAppTheme.typography.labelLarge,
            )
            Text(
                text = centerText,
                style = LearningZoneAppTheme.typography.titleLarge,
            )
            Text(
                text = bottomText,
                style = LearningZoneAppTheme.typography.titleLarge,
            )
        }
    }
}

@Composable
fun EbookCard(onCardClick: () -> Unit) {
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
                imageVector = ImageVector.vectorResource(id = R.drawable.bookdoodle),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "E-book",
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
                imageVector = ImageVector.vectorResource(id = R.drawable.laptopdoodle),
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
fun EbookBottomSheet(onDismiss: () -> Unit, sheetState: SheetState) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = LearningZoneAppTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "E-book ΑΕΠΠ",
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
                    text = "Ένας απλός και πρακτικός οδηγός για να κατανοήσεις τις βασικές έννοιες του μαθήματος εύκολα και γρήγορα.",
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
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "Δείξε μου το e-book",
                    style = LearningZoneAppTheme.typography.labelLarge,
                )
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
                        .padding(8.dp),
                    topText = "Ερωτήσεις",
                    centerText = "Σύνολο",
                    bottomText = appStats?.totalQuestions.toString(),
                    backgroundColor = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.tertiary)
                )
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
                    centerText = "Σύνολο",
                    bottomText = appStats?.totalAnswers.toString(),
                    backgroundColor = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.quaternary)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                SquareCard(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    topText = "Απαντήσεις",
                    centerText = "Σωστές",
                    bottomText = appStats?.totalCorrectAnswers.toString(),
                    backgroundColor = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.secondary)
                )
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
                    centerText = "Λάθος",
                    bottomText = appStats?.totalWrongAnswers.toString(),
                    backgroundColor = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.primary)
                )
            }
        }
    }
}

@Composable
fun GuyLottie(modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.learning))
    val progress by animateLottieCompositionAsState(composition, iterations = 3)

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = { progress }
    )
}

@Composable
fun RightLottie(modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.right))
    val progress by animateLottieCompositionAsState(composition)

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = { progress }
    )
}

@Composable
fun WrongLottie(modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.wrong))
    val progress by animateLottieCompositionAsState(composition)

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = { progress }
    )
}

@Composable
fun CrownLottie(modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.crown))
    val progress by animateLottieCompositionAsState(composition)

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = { progress }
    )
}

@Composable
fun WarningLottie(modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.warning))
    val progress by animateLottieCompositionAsState(composition)

    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR,
            value = LearningZoneAppTheme.colorScheme.onBackground.toArgb(),
            keyPath = arrayOf("**")
        )
    )

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = { progress },
        dynamicProperties = dynamicProperties
    )
}

@Composable
fun QuestionLottie(modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.question))
    val progress by animateLottieCompositionAsState(composition)

    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR,
            value = LearningZoneAppTheme.colorScheme.onBackground.toArgb(),
            keyPath = arrayOf("**")
        )
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier,
        dynamicProperties = dynamicProperties
    )
}

@Composable
fun CustomLottie(modifier: Modifier, resId: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resId = resId))
    val progress by animateLottieCompositionAsState(composition)

    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR,
            value = LearningZoneAppTheme.colorScheme.onBackground.toArgb(),
            keyPath = arrayOf("**")
        )
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier,
        dynamicProperties = dynamicProperties
    )
}
