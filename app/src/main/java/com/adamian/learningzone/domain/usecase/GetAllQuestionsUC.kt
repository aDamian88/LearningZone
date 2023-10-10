package com.adamian.learningzone.domain.usecase

import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllQuestionsUC @Inject constructor(
    private val repository: QuestionRepository
) {
    operator fun invoke(): Flow<List<QuestionItem>> = flow {
        val questions = repository.getAllQuestions()
        emit(questions)
    }
}