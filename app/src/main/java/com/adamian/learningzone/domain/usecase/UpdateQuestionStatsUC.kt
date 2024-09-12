package com.adamian.learningzone.domain.usecase

import com.adamian.learningzone.domain.repository.QuestionRepository
import javax.inject.Inject

class UpdateQuestionStatsUC @Inject constructor(
    private val repository: QuestionRepository
) {
    suspend fun incrementRight(questionId: Int) {
        repository.incrementRight(questionId)
    }

    suspend fun incrementWrong(questionId: Int) {
        repository.incrementWrong(questionId)
    }

    suspend fun incrementAnswered(questionId: Int) {
        repository.incrementAnswered(questionId)
    }

}
