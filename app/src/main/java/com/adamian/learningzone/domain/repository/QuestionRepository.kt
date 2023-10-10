package com.adamian.learningzone.domain.repository

import com.adamian.learningzone.domain.model.QuestionItem

interface QuestionRepository {

    suspend fun saveQuestion(questionItem: QuestionItem)

    suspend fun getAllQuestions(): List<QuestionItem>

}