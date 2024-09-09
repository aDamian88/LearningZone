package com.adamian.learningzone.ui.chapterscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.adamian.learningzone.R
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme

@Composable
fun ChapterScreen(navController: NavController) {

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .verticalScroll(rememberScrollState())

                ) {

                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.home_icon),
                        tint = Color.Unspecified,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(20.dp)
                            .size(70.dp)
                            .clickable { navController.popBackStack() },
                    )

                    Spacer(modifier = Modifier.padding(20.dp))

                    ChapterCard(
                        iconResId = R.drawable.problem_analysis,
                        title = "Κεφάλαιο 1",
                        subtitle = "Ανάλυση Προβλήματος"
                    )
                    ChapterCard(
                        iconResId = R.drawable.basic_algorithm_concept,
                        title = "Κεφάλαιο 2",
                        subtitle = "Βασικές Έννοιες Αλγορίθμων"
                    )
                    ChapterCard(
                        iconResId = R.drawable.data_stractures_algorithms,
                        title = "Κεφάλαιο 3",
                        subtitle = "Δομές Δεδομένων και Αλγόριθμοι"
                    )

                    ChapterCard(
                        iconResId = R.drawable.algorithm_design_techniques,
                        title = "Κεφάλαιο 4",
                        subtitle = "Τεχνικές Σχεδίασης Αλγόριθμων"
                    )

                    ChapterCard(
                        iconResId = R.drawable.introduction_programming,
                        title = "Κεφάλαιο 6",
                        subtitle = "Εισαγωγή στον Προγραμματισμό"
                    )

                    ChapterCard(
                        iconResId = R.drawable.basic_programming_concepts,
                        title = "Κεφάλαιο 7",
                        subtitle = "Βασικές Έννοιες Προγραμματισμού"
                    )

                    ChapterCard(
                        iconResId = R.drawable.select_n_repeat,
                        title = "Κεφάλαιο 8",
                        subtitle = "Επιλογή και Επανάληψη"
                    )

                    ChapterCard(
                        iconResId = R.drawable.matrix,
                        title = "Κεφάλαιο 9",
                        subtitle = "Πίνακες"
                    )
                    ChapterCard(
                        iconResId = R.drawable.subprograms,
                        title = "Κεφάλαιο 10",
                        subtitle = "Υποπρογράμματα"
                    )
                    ChapterCard(
                        iconResId = R.drawable.debbuging,
                        title = "Κεφάλαιο 13",
                        subtitle = "Εκσφαλμάτωση Προγράμματος"
                    )

                }
            }
        }
    }
}

@Composable
fun ChapterCard(
    iconResId: Int,
    title: String,
    subtitle: String
) {
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
                    style = LearningZoneAppTheme.typography.titleLarge
                )
                Text(
                    text = subtitle,
                    style = LearningZoneAppTheme.typography.labelLarge,
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
    }
}
