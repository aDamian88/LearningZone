package com.adamian.learningzone.ui.quizscreen


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.adamian.learningzone.R
import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.ui.homescreen.CustomLottie
import com.adamian.learningzone.ui.homescreen.QuestionLottie
import com.adamian.learningzone.ui.homescreen.RightLottie
import com.adamian.learningzone.ui.homescreen.WarningLottie
import com.adamian.learningzone.ui.homescreen.WrongLottie
import com.adamian.learningzone.ui.navigation.NavRoute
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme
import com.adamian.learningzone.ui.theme.LearningZoneAppTheme.neonColor
import kotlinx.coroutines.launch

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

    BackHandler {}

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LearningZoneAppTheme.colorScheme.background),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.exitQuizSheet() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = LearningZoneAppTheme.colorScheme.onBackground,
                        contentDescription = null,
                        modifier = Modifier.size(34.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(16.dp)
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
                .background(LearningZoneAppTheme.colorScheme.background)
        ) {
            currentQuestion?.let { question ->

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 90.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Text(
                            modifier = Modifier,
                            text = "Eρώτηση ${currentQuestionIndex + 1}/${questions.size}",
                            style = LearningZoneAppTheme.typography.labelNormal,
                            color = LearningZoneAppTheme.colorScheme.onBackground
                        )
                    }
                    item {
                        Text(
                            text = question.title,
                            modifier = Modifier.padding(8.dp),
                            style = LearningZoneAppTheme.typography.labelLarge,
                            color = LearningZoneAppTheme.colorScheme.onBackground
                        )
                    }

                    item {
                        QuestionBox(question = question.question)
                    }

                    items(question.options.filterNotNull().filter { it.isNotBlank() }) { option ->
                        AnswerCard(
                            answer = option,
                            isSelected = selectedAnswer == option,
                            onClick = { viewModel.selectAnswer(option) }
                        )
                    }
                }

                SubmitAnswerButton(
                    enabled = selectedAnswer != null,
                    onClick = {
                        selectedAnswer?.let { answer ->
                            viewModel.submitAnswer(answer = answer)
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
        containerColor = if (isCorrect) LearningZoneAppTheme.colorScheme.success else LearningZoneAppTheme.colorScheme.error,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = null
    ) {
        BackHandler {}

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 48.dp)
                .navigationBarsPadding()
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                if (isCorrect) {
                    RightLottie(
                        modifier = Modifier.size(48.dp)
                    )
                } else {
                    WrongLottie(
                        modifier = Modifier.size(48.dp)
                    )
                }

                Text(
                    text = if (isCorrect) "Σωστά" else "Θέλει διόρθωση",
                    style = LearningZoneAppTheme.typography.titleNormal,
                    modifier = Modifier.weight(1f)
                )
            }

            if (!isCorrect) {
                Text(
                    text = "Η σωστή απάντηση είναι: ${question.correctOption}",
                    style = LearningZoneAppTheme.typography.bodyBold,
                )
            }

            if (revealText) {
                Text(
                    text = "Απάντηση: ${question.answerDescription}",
                    style = LearningZoneAppTheme.typography.body,
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
                    Text(
                        text = "Γιατί;",
                        style = LearningZoneAppTheme.typography.bodyBold,
                    )
                }

                Button(
                    onClick = {
                        viewModel.nextQuestion()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Κατάλαβα",
                        style = LearningZoneAppTheme.typography.bodyBold,
                    )
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
        containerColor = LearningZoneAppTheme.colorScheme.background,
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 4.dp)
                    .size(40.dp),
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.backbagdoodle
                ),
                tint = Color.Unspecified,
                contentDescription = null
            )

            Text(
                text = "Θέλεις να φύγεις από το κουίζ;",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 24.dp, vertical = 4.dp),
                textAlign = TextAlign.Center,
                style = LearningZoneAppTheme.typography.labelLarge,
                color = LearningZoneAppTheme.colorScheme.onBackground
            )

            QuestionLottie(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 4.dp)
                    .size(40.dp)
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
                Text(
                    text = "Συνέχεια μαθήματος",
                    style = LearningZoneAppTheme.typography.bodyBold,
                )
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
                Text(
                    text = "Έξόδος",
                    style = LearningZoneAppTheme.typography.bodyBold,
                )
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
        containerColor = LearningZoneAppTheme.colorScheme.background,
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 4.dp)
                    .size(40.dp),
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.glassesdoodle
                ),
                tint = Color.Unspecified,
                contentDescription = null
            )

            Text(
                text = "Έκανες κάποια λάθη, πάμε να τα δούμε...",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 24.dp, vertical = 4.dp),
                textAlign = TextAlign.Center,
                style = LearningZoneAppTheme.typography.labelLarge,
                color = LearningZoneAppTheme.colorScheme.onBackground
            )

            WarningLottie(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 4.dp)
                    .size(40.dp)
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
        }
    }
}

@Composable
fun QuestionBox(question: String) {

    val leftIcon = remember(question) { randomDoodleIcon() }
    val rightIcon = remember(question) { randomDoodleIcon(exclude = leftIcon) }
    val lottie = remember(question) { randomLottie() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Icon(
                    imageVector = ImageVector.vectorResource(id = leftIcon),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )

                Icon(
                    imageVector = ImageVector.vectorResource(id = rightIcon),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 16.dp, end = 16.dp),
                text = question,
                style = LearningZoneAppTheme.typography.titleNormal,
                color = LearningZoneAppTheme.colorScheme.onBackground
            )

            key(lottie) {
                CustomLottie(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(84.dp),
                    resId = lottie
                )
            }
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
                LearningZoneAppTheme.colorScheme.tertiary
            else
                LearningZoneAppTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = neonColor.copy(alpha = 0.8f),
                spotColor = neonColor.copy(alpha = 0.8f)
            )
            .clickable { onClick() },
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
                textAlign = TextAlign.Center,
                color = if (isSelected)
                    LearningZoneAppTheme.colorScheme.onTertiary
                else
                    LearningZoneAppTheme.colorScheme.topSurface
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
    val backgroundColor =
        if (enabled)
            Color(LearningZoneAppTheme.colorScheme.quaternary.toArgb())
        else Color(LearningZoneAppTheme.colorScheme.surface.toArgb())

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
                text = "Υποβολή",
                style = LearningZoneAppTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                color = if (enabled)
                    LearningZoneAppTheme.colorScheme.onQuaternary
                else
                    LearningZoneAppTheme.colorScheme.topSurface
            )
        }
    }
}
