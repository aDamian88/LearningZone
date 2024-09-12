package com.adamian.learningzone.domain.repository

import com.adamian.learningzone.data.local.QuestionEntity
import com.adamian.learningzone.domain.model.QuestionItem

interface QuestionRepository {
    suspend fun saveQuestion(questionEntity: QuestionEntity)
    suspend fun getAllQuestions(): List<QuestionEntity>
    suspend fun getQuestionsByChapter(chapterId: Int): List<QuestionEntity>
    suspend fun insertAll(vararg questions: QuestionEntity)
    suspend fun isDatabaseEmpty(): Boolean
    suspend fun incrementRight(questionId: Int)
    suspend fun incrementWrong(questionId: Int)
    suspend fun incrementAnswered(questionId: Int)
}