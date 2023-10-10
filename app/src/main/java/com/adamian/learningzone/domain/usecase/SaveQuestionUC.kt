package com.adamian.learningzone.domain.usecase

import com.adamian.learningzone.domain.mappers.toQuestionEntity
import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveQuestionUC @Inject constructor(
    private val repository: QuestionRepository
) {
    operator fun invoke(
        questionItem: QuestionItem
    ): Flow<QuestionItem> = flow {
        repository.saveQuestion(questionItem.toQuestionEntity())
    }
}