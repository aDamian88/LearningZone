package com.adamian.learningzone.ui.chapterscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.adamian.learningzone.R
import com.adamian.learningzone.domain.usecase.GetChapterStatusFirstPassUC
import com.adamian.learningzone.ui.navigation.NavRoute
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme.neonColor

@Composable
fun ChapterScreen(
    navController: NavController,
    viewModel: ChapterScreenVM = hiltViewModel()
) {
    val appProgress by viewModel.appStats.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadChapterLearningProgress()
    }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LearningZoneAppTheme.colorScheme.background)
                    .padding(horizontal = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                        tint = LearningZoneAppTheme.colorScheme.onBackground,
                        contentDescription = null,
                        modifier = Modifier.size(34.dp)
                    )
                }
                Text(
                    text = "Κεφάλαια",
                    style = LearningZoneAppTheme.typography.titleLarge,
                    color = LearningZoneAppTheme.colorScheme.onBackground
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(LearningZoneAppTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.15f)
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                ) {

                    val totalProgress = appProgress?.completionPercentage?.toFloat() ?: 0f
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(end = 8.dp),
                            text = "Συνολικό ποσοστό ολοκλήρωσης:",
                            style = LearningZoneAppTheme.typography.labelLarge,
                            color = LearningZoneAppTheme.colorScheme.onBackground
                        )
                        Text(
                            modifier = Modifier,
                            text = "${(totalProgress * 100).toInt()}%",
                            style = LearningZoneAppTheme.typography.labelLarge,
                            color = LearningZoneAppTheme.colorScheme.onBackground
                        )
                    }

                    appProgress?.completionPercentage?.toFloat()?.let {
                        LinearProgressIndicator(
                            progress = { it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .height(24.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            color = LearningZoneAppTheme.colorScheme.primary,
                            trackColor = Color.Transparent
                        )
                    }
                }

                ChapterListContent(
                    viewModel = viewModel,
                    navController = navController,
                    modifier = Modifier.weight(0.9f)
                )
            }
        }
    }
}

@Composable
fun ChapterListContent(
    viewModel: ChapterScreenVM,
    navController: NavController,
    modifier: Modifier
) {

    val chapterProgress by viewModel.chapterLearningProgress.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    var selectedChapterId by rememberSaveable { mutableIntStateOf(-1) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        LazyColumn(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(LearningZoneAppTheme.colorScheme.topSurface),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val chapters = listOf(
                ChapterData(1, "1. Ανάλυση Προβλήματος"),
                ChapterData(
                    2,
                    "2. Βασικές Έννοιες Αλγορίθμων"
                ),
                ChapterData(
                    3,
                    "3. Δομές Δεδομένων και Αλγόριθμοι"
                ),
                ChapterData(
                    4,
                    "4. Τεχνικές Σχεδίασης Αλγόριθμων"
                ),
                ChapterData(
                    6,
                    "6. Εισαγωγή στον Προγραμματισμό"
                ),
                ChapterData(
                    7,
                    "7. Βασικές Έννοιες Προγραμματισμού"
                ),
                ChapterData(8, "8. Επιλογή και Επανάληψη"),
                ChapterData(9, "9. Πίνακες"),
                ChapterData(10, "10. Υποπρογράμματα"),
                ChapterData(13, "13. Εκσφαλμάτωση Προγράμματος")
            )
            items(
                items = chapters,
                key = { it.id }
            ) { chapter ->
                ChapterCardWithProgress(
                    chapter = chapter,
                    chapterProgress = chapterProgress,
                    onClick = { chapterId ->
                        val isChapterCompleted = checkTheChapter(
                            chapterId = chapterId,
                            chapterProgress = chapterProgress
                        )
                        if (isChapterCompleted) {
                            selectedChapterId = chapterId
                            showDialog = true
                        } else {
                            navController.navigate(
                                NavRoute.quizRoute(
                                    chapterId = chapterId,
                                    isRecap = 0
                                )
                            )
                        }
                    }
                )
            }
        }

        if (showDialog) {
            InfoDialog(
                onDismiss = { showDialog = false },
                onConfirm = {
                    navController.navigate(
                        NavRoute.quizRoute(
                            chapterId = selectedChapterId,
                            isRecap = 1
                        )
                    )
                }
            )
        }

    }
}

@Composable
fun ChapterCardWithProgress(
    chapter: ChapterData,
    chapterProgress: List<GetChapterStatusFirstPassUC.ChapterStats>,
    onClick: (Int) -> Unit
) {

    ChapterCard(
        id = chapter.id,
        title = chapter.title,
        chapterProgress = chapterProgress,
        onClick = onClick
    )
}

data class ChapterData(
    val id: Int,
    val title: String,
)

fun checkTheChapter(
    chapterId: Int,
    chapterProgress: List<GetChapterStatusFirstPassUC.ChapterStats>
): Boolean {
    val progress = chapterProgress
        .find { it.chapterId == chapterId }
        ?.totalProgress
        ?: 0.0f
    return progress >= 1
}

@Composable
fun InfoDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = LearningZoneAppTheme.colorScheme.surface,
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.coffeedoodle
                ),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        },
        title = {
            Text(
                text = "Ολοκληρωμένο κεφάλαιο!\nΠάμε επανάληψη κεφαλαίου;",
                style = LearningZoneAppTheme.typography.labelLarge,
                color = LearningZoneAppTheme.colorScheme.onBackground
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Κλείσιμο",
                    style = LearningZoneAppTheme.typography.labelLarge,
                    color = LearningZoneAppTheme.colorScheme.primary
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = "Επανάληψη",
                    style = LearningZoneAppTheme.typography.labelLarge,
                    color = LearningZoneAppTheme.colorScheme.primary
                )
            }
        }
    )
}

@Composable
fun ChapterCard(
    id: Int,
    title: String,
    chapterProgress: List<GetChapterStatusFirstPassUC.ChapterStats>,
    onClick: (Int) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.surface),
        modifier = Modifier
            .clickable { onClick(id) }
            .padding(16.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = neonColor.copy(alpha = 0.8f),
                spotColor = neonColor.copy(alpha = 0.8f)
            )
            .fillMaxWidth(),
    ) {
        Column {
            Text(
                modifier = Modifier.padding(16.dp),
                text = title,
                style = LearningZoneAppTheme.typography.labelLarge,
                color = LearningZoneAppTheme.colorScheme.onBackground
            )

            val stats = chapterProgress.firstOrNull { it.chapterId == id }
            val progress = stats?.totalProgress ?: 0f
            val totalQuestions = stats?.totalQuestions ?: 0
            val answeredQuestions = stats?.answeredQuestions ?: 0

            StatsChipsRow(
                totalQuestions = totalQuestions,
                answeredQuestions = answeredQuestions
            )

            ChapterProgressRow(progress)
        }
    }
}

@Composable
fun ChapterProgressRow(progress: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(LearningZoneAppTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(LearningZoneAppTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = LearningZoneAppTheme.typography.labelNormal,
                    color = LearningZoneAppTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(LearningZoneAppTheme.colorScheme.background)
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    color = LearningZoneAppTheme.colorScheme.primary,
                    trackColor = Color.Transparent
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StatsChipsRow(
    totalQuestions: Int,
    answeredQuestions: Int
) {
    val chipData = listOf(
        Triple("Ερωτήσεις", totalQuestions.toString(), LearningZoneAppTheme.colorScheme.secondary),
        Triple(
            "Απαντήσεις",
            answeredQuestions.toString(),
            LearningZoneAppTheme.colorScheme.quaternary
        ),
    )

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        chipData.forEach { (label, value, bgColor) ->
            StatChip(label, value, bgColor)
        }
    }
}

@Composable
fun StatChip(label: String, value: String, backgroundColor: Color) {
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = "$label: $value",
            style = LearningZoneAppTheme.typography.labelNormal,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}
