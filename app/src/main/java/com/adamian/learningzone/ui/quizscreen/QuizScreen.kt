package com.adamian.learningzone.ui.quizscreen


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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.ui.navigation.NavRoute
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme
import kotlinx.coroutines.launch

//// next steps
//// - complete quiz screen
//// - lets check the errors screen (after the re-quiz)
//// - Animations
//// ? exit bug

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    chapterId: Int,
    navController: NavController,
    viewModel: QuizScreenViewModel = hiltViewModel()
) {

    LaunchedEffect(chapterId) {
        viewModel.setChapterId(chapterId)
    }

    val questions by viewModel.questions.collectAsState()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()
    val selectedAnswer by viewModel.selectedAnswer.collectAsState()
    val showResult by viewModel.showResult.collectAsState()
    val showExit by viewModel.showExit.collectAsState()
    val quizFinished by viewModel.quizFinished.collectAsState()
    val correctCount by viewModel.correctCount.collectAsState()
    val wrongCount by viewModel.wrongCount.collectAsState()
    val progress by viewModel.progress.collectAsState()
    val wrongList = viewModel.wrongQuestions

    val currentQuestion = questions.getOrNull(currentQuestionIndex)

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.exitQuizSheet() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(16.dp) // Increased height
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

    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            currentQuestion?.let { question ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 90.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = question.title,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                        style = LearningZoneAppTheme.typography.titleLarge
                    )

                    QuestionBox(question = question.question)

                    Spacer(modifier = Modifier.padding(10.dp))

                    question.options.filterNotNull().filter { it.isNotBlank() }
                        .forEach { option ->
                            AnswerCard(
                                answer = option,
                                isSelected = selectedAnswer == option,
                                onClick = {
                                    viewModel.selectAnswer(option)
                                }
                            )
                        }
                }

                SubmitAnswerButton(
                    enabled = selectedAnswer != null,
                    onClick = {
                        selectedAnswer?.let {
                            viewModel.submitAnswer(answer = selectedAnswer!!) // Todo this validation should change
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .zIndex(1f)
                )

                if (showResult && !quizFinished) {
                    AnswerBottomSheet(
                        viewModel = viewModel,
                        question = question,
                        isCorrect = selectedAnswer == currentQuestion.correctOption
                    )
                }

                if (showExit) {
                    ExitBottomSheet(
                        navController = navController,
                        viewModel = viewModel
                    )
                }

                if (quizFinished) {
                    if (wrongList.isNotEmpty()) {
                        CorrectionBottomSheet(
                            viewModel = viewModel
                        )
                    } else {
                        navController.navigate(
                            NavRoute.Summary.createRoute(
                                correctCount,
                                wrongCount
                            )
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnswerBottomSheet(
    viewModel: QuizScreenViewModel,
    question: QuestionItem,
    isCorrect: Boolean
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { false }
    )
    var revealText by remember { mutableStateOf(false) }
    var whyButtonClicked by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = {},
        sheetState = sheetState,
        scrimColor = Color.Transparent,
        containerColor = if (isCorrect) Color(0xFFDFF0D8) else Color(0xFFF2DEDE),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            awaitPointerEvent()
                        }
                    }
                },
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (isCorrect) {
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        modifier = Modifier.weight(0.1f),
                        contentDescription = "correct"
                    )
                    Text(
                        text = "Σωστά",
                        style = LearningZoneAppTheme.typography.titleNormal,
                        modifier = Modifier.weight(0.9f)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        modifier = Modifier.weight(0.1f),
                        contentDescription = "wrong"
                    )
                    Text(
                        text = "Θέλει διόρθωση",
                        style = LearningZoneAppTheme.typography.titleNormal,
                        modifier = Modifier.weight(0.9f),
                    )
                }
            }

            if (!isCorrect) {
                Text(
                    text = "Η σωστή απάντηση είναι: ${question.correctOption}",
                    fontSize = 16.sp
                )
            }

            if (revealText) {
                Text(
                    text = "Απάντηση: ${question.answerDescription}",
                    fontSize = 16.sp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        revealText = true
                        whyButtonClicked = true
                    },
                    enabled = !whyButtonClicked,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Γιατί;")
                }

                Button(
                    onClick = {
                        viewModel.nextQuestion()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Κατάλαβα")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExitBottomSheet(navController: NavController, viewModel: QuizScreenViewModel) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
                viewModel.closeExitQuizSheet()
            }
        },
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            awaitPointerEvent()
                        }
                    }
                },
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Icon(
                imageVector = Icons.Default.Info,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 4.dp),
                contentDescription = "Close"
            )
            Text(
                text = "Θέλεις να φύγεις από το μάθημα;",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 4.dp),
                style = LearningZoneAppTheme.typography.titleNormal
            )
            Button(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                        viewModel.closeExitQuizSheet()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 4.dp)
            ) {
                Text("Συνέχεια μαθήματος")
            }

            Button(
                onClick = {
                    navController.navigateUp()
                    viewModel.closeExitQuizSheet()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 4.dp)
            ) {
                Text("Έξόδος")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CorrectionBottomSheet(viewModel: QuizScreenViewModel) {
    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { false }
    )

    ModalBottomSheet(
        onDismissRequest = {},
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            awaitPointerEvent()
                        }
                    }
                },
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Icon(
                imageVector = Icons.Default.Info,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 4.dp),
                contentDescription = "Close"
            )
            Text(
                text = "Έκανες κάποια λάθη, πάμε να τα δούμε...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 4.dp),
                style = LearningZoneAppTheme.typography.titleNormal
            )
            Button(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                        viewModel.loadWrongQuestions()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 4.dp)
            ) {
                Text("Ας τα διορθώσουμε!")
            }

//            Button(
//                onClick = {
//                    navController.navigateUp()
//                    viewModel.closeExitQuizSheet()
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 24.dp, vertical = 4.dp)
//            ) {
//                Text("Έξόδος")
//            }
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

            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                text = question,
                style = LearningZoneAppTheme.typography.titleNormal
            )
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
fun AnswerCard(
    answer: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                LearningZoneAppTheme.colorScheme.background
            else
                Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = answer,
                style = LearningZoneAppTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SubmitAnswerButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (enabled)
        Color(0xFF4CAF50) else Color(0xFFBDBDBD)

    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) { onClick() },
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Submit",
                style = LearningZoneAppTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}
