package com.adamian.learningzone.ui.quizscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.adamian.learningzone.R
import com.adamian.learningzone.ui.homescreen.CrownLottie
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme

@Composable
fun SummaryScreen(
    correctCount: Int,
    wrongCount: Int,
    onNavigateHome: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        DecorativeIconsBackground()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            item {
                CrownLottie(
                    modifier = Modifier.size(120.dp)
                )
            }

            item {
                Text(
                    "Το κουΐζ ολοκληρώθηκε!",
                    style = LearningZoneAppTheme.typography.titleLarge,
                    color = LearningZoneAppTheme.colorScheme.onBackground
                )
            }

            item {
                Text(
                    "Σωστές απαντήσεις: $correctCount",
                    style = LearningZoneAppTheme.typography.labelNormal,
                    color = LearningZoneAppTheme.colorScheme.onBackground
                )
            }

            item {
                Text(
                    "Λάθος απαντήσεις: $wrongCount",
                    style = LearningZoneAppTheme.typography.labelNormal,
                    color = LearningZoneAppTheme.colorScheme.onBackground
                )
            }

            item {
                Button(onClick = onNavigateHome) {
                    Text("Πίσω")
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
            Pair(0, 1),              Pair(3, 1),
            Pair(0, 2),              Pair(3, 2),
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
