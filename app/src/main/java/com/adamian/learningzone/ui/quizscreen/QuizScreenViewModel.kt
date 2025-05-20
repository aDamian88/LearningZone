package com.adamian.learningzone.ui.quizscreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.domain.usecase.GetQuestionsUC
import com.adamian.learningzone.domain.usecase.UpdateQuestionStatsUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizScreenViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUC,
    private val updateQuestionStatsUC: UpdateQuestionStatsUC
) : ViewModel() {

    // todo merge all flows to one uiState
    private val _questions = MutableStateFlow<List<QuestionItem>>(emptyList())
    val questions: StateFlow<List<QuestionItem>> = _questions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _selectedAnswer = MutableStateFlow<String?>(null)
    val selectedAnswer: StateFlow<String?> = _selectedAnswer.asStateFlow()

    private val _showResult = MutableStateFlow(false)
    val showResult: StateFlow<Boolean> = _showResult.asStateFlow()

    private val _showExit = MutableStateFlow(false)
    val showExit: StateFlow<Boolean> = _showExit.asStateFlow()

    private val _quizFinished = MutableStateFlow(false)
    val quizFinished: StateFlow<Boolean> = _quizFinished.asStateFlow()

    private val _correctCount = MutableStateFlow(0)
    val correctCount: StateFlow<Int> = _correctCount.asStateFlow()

    private val _wrongCount = MutableStateFlow(0)
    val wrongCount: StateFlow<Int> = _wrongCount.asStateFlow()

    val progress: StateFlow<Float> =
        combine(_currentQuestionIndex, _questions) { index, questions ->
            if (questions.isNotEmpty()) {
                (index + 1).toFloat() / questions.size
            } else {
                0f
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, 0f)

    private var chapterId: Int = 0

    fun setChapterId(chapterId: Int) {
        this.chapterId = chapterId
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            val questions = getQuestionsUseCase(chapterId)
            _questions.value = questions
        }
    }

    private val _wrongQuestions = mutableStateListOf<Int>()
    val wrongQuestions: List<Int> get() = _wrongQuestions

    fun loadWrongQuestions() {
        viewModelScope.launch {
            val allQuestions = _questions.value
            val filtered = allQuestions.filter { it.id in wrongQuestions }
            _questions.value = filtered
            _currentQuestionIndex.value = 0
            _quizFinished.value = false
            _selectedAnswer.value = null
            _wrongQuestions.clear()
        }
    }

    fun selectAnswer(answer: String) {
        _selectedAnswer.value = answer
    }

    fun submitAnswer(answer: String) {
        _selectedAnswer.value = answer
        _showResult.value = true

        val currentQuestion = _questions.value.getOrNull(_currentQuestionIndex.value)
        currentQuestion?.let {
            updateStats(it.id, answer == it.correctOption)
        }
    }

    fun exitQuizSheet() {
        _showExit.value = true
    }

    fun closeExitQuizSheet() {
        _showExit.value = false
    }

    fun nextQuestion() {
        if (_currentQuestionIndex.value < _questions.value.size - 1) {
            _currentQuestionIndex.value++
            _selectedAnswer.value = null
        } else {
            _quizFinished.value = true
        }
        _showResult.value = false
    }

    private fun updateStats(questionId: Int, isCorrect: Boolean) {
        viewModelScope.launch {
            if (isCorrect) {
                _correctCount.value++
                updateQuestionStatsUC.incrementRight(questionId)
            } else {
                _wrongCount.value++
                updateQuestionStatsUC.incrementWrong(questionId)
                _wrongQuestions.add(questionId)
            }
        }
    }
}
