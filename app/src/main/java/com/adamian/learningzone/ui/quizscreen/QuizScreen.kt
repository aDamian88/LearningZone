package com.adamian.learningzone.ui.quizscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.adamian.learningzone.R
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme

@Composable
fun QuizScreen() {

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
                    QuestionBox()
                    Spacer(modifier = Modifier.padding(10.dp))
                    AnswerCard()
                    AnswerCard()
                    AnswerCard()
                    AnswerCard()
                }

            }
        }
    }
}

@Composable
fun QuestionBox() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 48.dp) // Add padding for margin
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(10.dp),
                clip = true
            )
            .clip(RoundedCornerShape(10.dp))
            .background(LearningZoneAppTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.student_mate),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "Ερώτηση ερώτηση ερώτηση ερώτηση",
                style = LearningZoneAppTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.padding(10.dp))

        }
    }
}

@Composable
fun AnswerCard() {
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
            Text(
                text = "Απάντηση απάντηση απάντηση απάντηση απάντηση",
                style = LearningZoneAppTheme.typography.labelLarge
            )
        }
    }
}