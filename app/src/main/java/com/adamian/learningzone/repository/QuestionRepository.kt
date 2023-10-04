package com.adamian.learningzone.repository

import com.adamian.learningzone.database.AppDao
import com.adamian.learningzone.model.QuestionItem
import com.adamian.learningzone.model.QuestionItem.Companion.mapToEntity
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val appDao: AppDao
){
    suspend fun saveQuestion(questionItem: QuestionItem){
        appDao.insertQuestion(mapToEntity(questionItem))
    }
}