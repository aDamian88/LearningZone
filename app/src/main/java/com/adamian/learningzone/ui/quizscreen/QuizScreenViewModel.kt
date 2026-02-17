package com.adamian.learningzone.ui.quizscreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.domain.usecase.GetQuestionsUC
import com.adamian.learningzone.domain.usecase.GetRecapQuestionsUC
import com.adamian.learningzone.domain.usecase.UpdateQuestionStatsUC
import com.adamian.learningzone.domain.usecase.UpdateQuizStatusUC
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
    private val getRecapQuestionsUC: GetRecapQuestionsUC,
    private val updateQuestionStatsUC: UpdateQuestionStatsUC,
    private val updateQuizStatusUC: UpdateQuizStatusUC
) : ViewModel() {

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
        combine(_currentQuestionIndex, _questions) { index, qs ->
            if (qs.isNotEmpty()) (index + 1).toFloat() / qs.size else 0f
        }.stateIn(viewModelScope, SharingStarted.Eagerly, 0f)

    private var chapterId: Int = 0
    private var isRecap: Int = 0 // 0 regular, 1 recap
    private val attemptedAtLeastOnce = mutableSetOf<Int>()
    private val wrongOnFirstAttempt = mutableSetOf<Int>()

    private val _wrongQuestions = mutableStateListOf<Int>()
    val wrongQuestions: List<Int> get() = _wrongQuestions

    fun setChapterId(chapterId: Int, isRecap: Int) {

        this.chapterId = chapterId
        this.isRecap = isRecap

        if (isRecap != 0) loadRecapQuestions() else loadQuestions()
    }

    private fun resetQuizUiForNewSet() {
        _currentQuestionIndex.value = 0
        _quizFinished.value = false
        _selectedAnswer.value = null
        _showResult.value = false
        _showExit.value = false
        _correctCount.value = 0
        _wrongCount.value = 0
        _wrongQuestions.clear()

        attemptedAtLeastOnce.clear()
        wrongOnFirstAttempt.clear()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            resetQuizUiForNewSet()
            _questions.value = getQuestionsUseCase(chapterId)
        }
    }

    private fun loadRecapQuestions() {
        viewModelScope.launch {
            resetQuizUiForNewSet()
            _questions.value = getRecapQuestionsUC(chapterId, limit = 10)
        }
    }

    fun loadWrongQuestions() {
        viewModelScope.launch {
            val allQuestions = _questions.value
            val filtered = allQuestions.filter { it.id in wrongQuestions }
            _questions.value = filtered
            _currentQuestionIndex.value = 0
            _quizFinished.value = false
            _selectedAnswer.value = null

            _wrongQuestions.clear()
            _showResult.value = false
        }
    }

    fun selectAnswer(answer: String) {
        _selectedAnswer.value = answer
    }

    fun submitAnswer(answer: String) {
        _selectedAnswer.value = answer
        _showResult.value = true

        val currentQuestion = _questions.value.getOrNull(_currentQuestionIndex.value) ?: return

        val isFirstAttempt = attemptedAtLeastOnce.add(currentQuestion.id)
        val isCorrect = answer == currentQuestion.correctOption

        updateStats(
            questionId = currentQuestion.id,
            isCorrect = isCorrect,
            isFirstAttempt = isFirstAttempt
        )
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
            viewModelScope.launch {
                _questions.value.firstOrNull()?.let { first ->
                    updateQuizStatusUC.completeQuiz(first.quizId)
                }
            }
        }
        _showResult.value = false
    }

    private fun updateStats(questionId: Int, isCorrect: Boolean, isFirstAttempt: Boolean) {
        viewModelScope.launch {
            if (isCorrect) {
                _correctCount.value++
                updateQuestionStatsUC.incrementRight(questionId)
            } else {
                _wrongCount.value++
                updateQuestionStatsUC.incrementWrong(questionId)

                _wrongQuestions.add(questionId)

                if (isFirstAttempt) {
                    wrongOnFirstAttempt.add(questionId)
                }
            }
        }
    }

    // ---------------- SUMMARY HELPERS ----------------

    val chapterTitle: String
        get() = _questions.value.firstOrNull()?.title.orEmpty()

    val totalQuestions: Int
        get() = _correctCount.value

    val extraAttempts: Int
        get() = _wrongCount.value

    val initialCorrectCount: Int
        get() = (totalQuestions - wrongOnFirstAttempt.size).coerceIn(0, totalQuestions)

    fun comprehensionPercent(k: Double = 0.10): Int {
        val score = 100.0 * kotlin.math.exp(-k * extraAttempts)
        return score.toInt().coerceIn(0, 100)
    }

    fun comprehensionMessage(): String {
        return when (comprehensionPercent()) {
            in 97..100 -> "Άριστη κατανόηση. Σχεδόν μηδενικές διορθώσεις."
            in 90..96 -> "Εξαιρετική κατανόηση. Πολύ λίγες διορθώσεις."
            in 82..89 -> "Πολύ καλή κατανόηση. Μικρός αριθμός διορθώσεων – συνέχισε έτσι."
            in 72..81 -> "Καλή κατανόηση. Κάνε μια σύντομη επανάληψη στα σημεία που δυσκόλεψαν."
            in 62..71 -> "Μέτρια κατανόηση. Χρειάζεται λίγη ακόμη εξάσκηση για σταθερότητα."
            in 50..61 -> "Χρειάζεται ενίσχυση. Δες τη θεωρία/παραδείγματα και ξαναπροσπάθησε."
            else -> "Χρειάζεται περισσότερη δουλειά. Προτείνεται επανάληψη και νέα προσπάθεια."
        }
    }

    fun stabilityLabel(): String = when {
        extraAttempts == 0 -> "Πολύ Υψηλή"
        extraAttempts in 1..2 -> "Υψηλή"
        extraAttempts in 3..4 -> "Μέτρια"
        else -> "Χαμηλή"
    }

    fun currentDateText(): String {
        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale("el", "GR"))
        return sdf.format(java.util.Date())
    }
}
