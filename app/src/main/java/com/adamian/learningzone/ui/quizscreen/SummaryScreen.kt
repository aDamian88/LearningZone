package com.adamian.learningzone.ui.quizscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.drawToBitmap
import com.adamian.learningzone.R
import com.adamian.learningzone.ui.homescreen.CircularProgressBar
import com.adamian.learningzone.ui.homescreen.CrownLottie
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme

@Composable
fun SummaryScreen(
    quizViewModel: QuizScreenViewModel,
    onNavigateToChapters: () -> Unit
) {
    val context = LocalContext.current
    val view = LocalView.current

    val chapterTitle = quizViewModel.chapterTitle
    val totalQuestions = quizViewModel.totalQuestions
    val extraAttempts = quizViewModel.extraAttempts
    val initialCorrect = quizViewModel.initialCorrectCount
    val comprehension = quizViewModel.comprehensionPercent()
    val message = quizViewModel.comprehensionMessage()
    val stability = quizViewModel.stabilityLabel()
    val dateText = quizViewModel.currentDateText()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LearningZoneAppTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
    ) {
        DecorativeIconsBackground()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            item {
                CrownLottie(modifier = Modifier.size(120.dp))
            }

            item {
                Text(
                    "Το κουΐζ ολοκληρώθηκε!",
                    style = LearningZoneAppTheme.typography.titleLarge
                )
            }

            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    tonalElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Ημερομηνία: $dateText")
                        Text("Κεφάλαιο: $chapterTitle")
                    }
                }
            }

            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    tonalElevation = 3.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {

                        CircularProgressBar(
                            percentage = comprehension / 100f,
                            number = 100,
                            radius = 52.dp,
                            strokeWidth = 10.dp
                        )

                        Text("Δείκτης Κατανόησης")
                        Text(
                            text = message,
                            style = LearningZoneAppTheme.typography.labelNormal,
                            color = LearningZoneAppTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    tonalElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("📌 Αρχικές σωστές: $initialCorrect / $totalQuestions")
                        Text("↩️ Επιπλέον προσπάθειες: $extraAttempts")
                        Text("📈 Σταθερότητα Απόδοσης: $stability")
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    Button(
                        onClick = {
                            val bitmap = view.drawToBitmap()
                            shareBitmap(context, bitmap)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Κοινοποίηση")
                    }

                    Button(
                        onClick = onNavigateToChapters,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Επιστροφή")
                    }
                }
            }
        }
    }
}

@Composable
fun DecorativeIconsBackground() {
    val icons = listOf(
        R.drawable.shoedoodle,
        R.drawable.papareairdoodle,
        R.drawable.notedoodle,
        R.drawable.bagdoodle,
        R.drawable.headphonesdoodle,
        R.drawable.girlbagdoodle,
        R.drawable.coffeedoodle
    )

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()

        val cols = 4
        val rows = 4

        val cellWidth = width / cols
        val cellHeight = height / rows

        val slots = listOf(
            Pair(0, 0), Pair(3, 0),
            Pair(0, 1), Pair(3, 1),
            Pair(0, 2), Pair(3, 2),
            Pair(0, 3), Pair(1, 3), Pair(2, 3), Pair(3, 3)
        )

        slots.forEachIndexed { index, (col, row) ->
            val x = col * cellWidth + cellWidth / 4
            val y = row * cellHeight + cellHeight / 4

            Icon(
                imageVector = ImageVector.vectorResource(
                    id = icons[index % icons.size]
                ),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(40.dp)
                    .graphicsLayer {
                        alpha = 0.15f
                        translationX = x
                        translationY = y
                    }
            )
        }
    }
}
