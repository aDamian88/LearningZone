package com.adamian.learningzone.ui.chapterscreen

import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import com.adamian.learningzone.R
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme

@Composable
fun ChapterScreen() {

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
                    Spacer(modifier = Modifier.padding(50.dp))

                    ChapterCard()
                    ChapterCard()
                    ChapterCard()
                    ChapterCard()
                    ChapterCard()
                }
            }
        }
    }

}

@Composable
fun ChapterCard() {
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
                    .size(100.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                Box(modifier = Modifier.fillMaxSize())
            }
            Spacer(modifier = Modifier.width(18.dp))
            Column {
                Text(
                    text = "Κεφάλαιο 1",
                    style = LearningZoneAppTheme.typography.titleLarge
                )
                Text(
                    text = "Όνομα κεφαλαίου",
                    style = LearningZoneAppTheme.typography.labelLarge
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