package com.adamian.learningzone.ui.quizscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adamian.learningzone.R
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme

@Composable
fun QuizScreen(
    chapterId: Int,
    viewModel: QuizScreenViewModel = hiltViewModel()
) {
    val questions by viewModel.questions.collectAsState()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()
    val selectedAnswer by viewModel.selectedAnswer.collectAsState()
    val showResult by viewModel.showResult.collectAsState()

    val currentQuestion = questions.getOrNull(currentQuestionIndex)

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

                Text(text = "Quiz for Chapter $chapterId")

                currentQuestion?.let {
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .verticalScroll(rememberScrollState())
                    ) {
                        QuestionBox(question = it.question)
                        Spacer(modifier = Modifier.padding(10.dp))
                        it.options.forEach { option ->
                            AnswerCard(
                                answer = option ?: "",
                                isSelected = selectedAnswer == option,
                                onClick = { viewModel.selectAnswer(option ?: "") }
                            )
                        }
                    }

                    if (showResult) {
                        ResultDialog(
                            isCorrect = selectedAnswer == it.correctOption,
                            onDismiss = { viewModel.nextQuestion() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionBox(question: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 48.dp)
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
                modifier = Modifier.padding(16.dp),
                text = question,
                style = LearningZoneAppTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.padding(10.dp))

        }
    }
}

@Composable
fun AnswerCard(answer: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            if (isSelected) Color.Green else LearningZoneAppTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = answer,
                style = LearningZoneAppTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun ResultDialog(isCorrect: Boolean, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (isCorrect) "Correct!" else "Incorrect") },
        text = { Text(text = if (isCorrect) "Well done!" else "Try again.") },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Next")
            }
        }
    )
}
