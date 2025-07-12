package com.adamian.learningzone.domain.usecase

import com.adamian.learningzone.domain.repository.QuizRepository
import javax.inject.Inject

class UpdateQuizStatusUC @Inject constructor(
    private val repository: QuizRepository
) {
    suspend fun completeQuiz(quizId: Int) {
        repository.completeQuiz(quizId)
    }
}