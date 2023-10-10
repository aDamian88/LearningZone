package com.adamian.learningzone

import QuizScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.ui.theme.LearningZoneTheme
import com.adamian.learningzone.ui.viewmodel.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

    private val questionViewModel: QuestionViewModel by viewModels()

    private var defaultQuestionList = emptyList<QuestionItem?>()

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
                    val questionListState by questionViewModel.allQuestionItems.collectAsState()

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
        questionViewModel.getAllQuestions()

        observeQuestions()

    }

    private fun observeQuestions() {
        val questions = questionViewModel.allQuestionItems
        Log.d(TAG, "observeQuestions: $questions")

        lifecycleScope.launch {
            questionViewModel.allQuestionItems.collectLatest {
                Log.d(TAG, "observeQuestions: ${questions.value}")
                defaultQuestionList = questions.value
            }
        }

    }
}
