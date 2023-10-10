package com.adamian.learningzone.domain.repository

import com.adamian.learningzone.data.local.QuestionEntity
import com.adamian.learningzone.domain.model.QuestionItem

interface QuestionRepository {

    suspend fun saveQuestion(questionEntity: QuestionEntity)

    suspend fun getAllQuestions(): List<QuestionItem>

}