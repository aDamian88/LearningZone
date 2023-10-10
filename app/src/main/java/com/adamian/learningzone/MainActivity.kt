package com.adamian.learningzone

import QuizScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.ui.theme.LearningZoneTheme
import com.adamian.learningzone.ui.viewmodel.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val questionViewModel: QuestionViewModel by viewModels()

    private val questionItems = arrayOf(
        QuestionItem(
            "new question",
            "new description",
            listOf("first option", "second option", "third option", "fourthOption"),
            "1"
        ),
        QuestionItem(
            "another question",
            "another description",
            listOf("first option", "second option", "third option", "fourthOption"),
            "2"
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // save questions
        questionItems.forEach {
            questionViewModel.saveQuestion(it)
        }
        setContent {
            LearningZoneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    questionViewModel.getAllQuestions()
                    val questionListState by questionViewModel.questionState.collectAsStateWithLifecycle()

                    if (questionListState.isNotEmpty()) {
                        var quizCompleted by remember { mutableStateOf(false) }

                        QuizScreen(questionListState[0]!!) {
                            quizCompleted = true
                        }
                    } else {
                        // Display a loading indicator or empty state message
                        Text(text = "Loading questions...")
                    }
                }
            }
        }

    }

}
