package com.adamian.learningzone.ui.chapterscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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

    val chapterProgress by viewModel.chapterLearningProgress.collectAsState()
    val appProgress by viewModel.appStats.collectAsState()
    var selectedChapterId by remember { mutableIntStateOf(0) }  // State to store selected chapter ID

    LaunchedEffect(Unit) {
        viewModel.loadChapterLearningProgress()
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(LearningZoneAppTheme.colorScheme.background),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(16.dp)
                        .padding(start = 8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(LearningZoneAppTheme.colorScheme.background)
                ) {
                    LinearProgressIndicator(
                        progress = { appProgress?.completionPercentage?.toFloat() ?: 0.0f },
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        color = LearningZoneAppTheme.colorScheme.quaternary,
                        trackColor = Color.Transparent,
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(LearningZoneAppTheme.colorScheme.background)
        ) {
            var showDialog by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .verticalScroll(rememberScrollState())
                ) {
                    val chapters = listOf(
                        ChapterData(
                            1,
                            R.drawable.problem_analysis,
                            "Κεφάλαιο 1",
                            "Ανάλυση Προβλήματος"
                        ),
                        ChapterData(
                            2,
                            R.drawable.basic_algorithm_concept,
                            "Κεφάλαιο 2",
                            "Βασικές Έννοιες Αλγορίθμων"
                        ),
                        ChapterData(
                            3,
                            R.drawable.data_stractures_algorithms,
                            "Κεφάλαιο 3",
                            "Δομές Δεδομένων και Αλγόριθμοι"
                        ),
                        ChapterData(
                            4,
                            R.drawable.algorithm_design_techniques,
                            "Κεφάλαιο 4",
                            "Τεχνικές Σχεδίασης Αλγόριθμων"
                        ),
                        ChapterData(
                            6,
                            R.drawable.introduction_programming,
                            "Κεφάλαιο 6",
                            "Εισαγωγή στον Προγραμματισμό"
                        ),
                        ChapterData(
                            7,
                            R.drawable.basic_programming_concepts,
                            "Κεφάλαιο 7",
                            "Βασικές Έννοιες Προγραμματισμού"
                        ),
                        ChapterData(
                            8,
                            R.drawable.select_n_repeat,
                            "Κεφάλαιο 8",
                            "Επιλογή και Επανάληψη"
                        ),
                        ChapterData(9, R.drawable.matrix, "Κεφάλαιο 9", "Πίνακες"),
                        ChapterData(10, R.drawable.subprograms, "Κεφάλαιο 10", "Υποπρογράμματα"),
                        ChapterData(
                            13,
                            R.drawable.debbuging,
                            "Κεφάλαιο 13",
                            "Εκσφαλμάτωση Προγράμματος"
                        )
                    )

                    chapters.forEach { chapter ->
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
                                    navController.navigate(NavRoute.quizRoute(chapterId))
                                }
                            }
                        )
                    }

                    if (showDialog) {
                        InfoDialog(onDismiss = { showDialog = false })  // Handle dialog visibility
                    }
                }
            }
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
        iconResId = chapter.iconResId,
        title = chapter.title,
        subtitle = chapter.subtitle,
        chapterProgress = chapterProgress,
        onClick = onClick
    )
}

data class ChapterData(
    val id: Int,
    val iconResId: Int,
    val title: String,
    val subtitle: String
)

fun checkTheChapter(chapterId: Int, chapterProgress: List<GetChapterStatusFirstPassUC.ChapterStats>): Boolean {
    val progress = chapterProgress
        .find { it.chapterId == chapterId }
        ?.totalProgress
        ?: 0.0f
    return progress >= 100
}

@Composable
fun InfoDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Ολοκληρωμένο κεφάλαιο") },
        text = { Text(text = "Μπορείς να συνεχίσεις σε άλλο κεφάλαιο.") },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Kλείσιμο")
            }
        }
    )
}

@Composable
fun ChapterCard(
    id: Int,
    iconResId: Int,
    title: String,
    subtitle: String,
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(LearningZoneAppTheme.colorScheme.secondary),
                    modifier = Modifier
                        .size(100.dp),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = iconResId),
                            tint = Color.Unspecified,
                            contentDescription = null,
                            modifier = Modifier.size(70.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(18.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        style = LearningZoneAppTheme.typography.titleLarge,
                        color = LearningZoneAppTheme.colorScheme.onBackground
                    )
                    Text(
                        text = subtitle,
                        style = LearningZoneAppTheme.typography.labelLarge,
                        color = LearningZoneAppTheme.colorScheme.onBackground,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.width(18.dp))
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.chapter_arrow_icon),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                )
            }

            val progress = chapterProgress
                .find { it.chapterId == id }
                ?.totalProgress
                ?: 0.0f

            val totalQuestions = chapterProgress
                .find { it.chapterId == id }
                ?.totalQuestions
                ?: 0

            val answeredQuestions = chapterProgress
                .find { it.chapterId == id }
                ?.answeredQuestions
                ?: 0

            val totalQuizzes= chapterProgress
                .find { it.chapterId == id }
                ?.totalQuizzes
                ?: 0

            val completedQuizzes = chapterProgress
                .find { it.chapterId == id }
                ?.completedQuizzes
                ?: 0

            Text(
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                text = "Συνολικά κουίζ: ${totalQuizzes}",
                style = LearningZoneAppTheme.typography.bodyBold,
                color = LearningZoneAppTheme.colorScheme.onBackground
            )
            Text(
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                text = "Ολοκληρωμένα κουίζ: ${completedQuizzes}",
                style = LearningZoneAppTheme.typography.bodyBold,
                color = LearningZoneAppTheme.colorScheme.onBackground
            )

            Text(
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                text = "Συνολικές ερωτήσεις: ${totalQuestions}",
                style = LearningZoneAppTheme.typography.bodyBold,
                color = LearningZoneAppTheme.colorScheme.onBackground
            )
            Text(
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                text = "απαντημένες ερωτήσεις: ${answeredQuestions}",
                style = LearningZoneAppTheme.typography.bodyBold,
                color = LearningZoneAppTheme.colorScheme.onBackground
            )
            Text(
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                text = "Ποσοστό ολοκλήρωσης: ${(progress * 100).toInt()}%",
                style = LearningZoneAppTheme.typography.bodyBold,
                color = LearningZoneAppTheme.colorScheme.onBackground
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(LearningZoneAppTheme.colorScheme.background),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp)
                        .padding(start = 8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(LearningZoneAppTheme.colorScheme.background)
                ) {
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        color = LearningZoneAppTheme.colorScheme.quaternary,
                        trackColor = Color.Transparent,
                    )
                }
            }
        }
    }
}
