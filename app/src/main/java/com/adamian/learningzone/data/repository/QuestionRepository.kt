package com.adamian.learningzone.data.repository

import android.util.Log
import com.adamian.learningzone.data.database.AppDao
import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.domain.model.QuestionItem.Companion.mapToEntity
import com.adamian.learningzone.domain.model.QuestionItem.Companion.mapToItem
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val appDao: AppDao
){
    private val TAG = "QuestionRepository"
    suspend fun saveQuestion(questionItem: QuestionItem){
        appDao.insertQuestion(mapToEntity(questionItem))
    }

    suspend fun getAllQuestions(): List<QuestionItem> {
        return appDao.getAllQuestions().map {
            Log.d(TAG, "getAllQuestions: $it")
            mapToItem(it) }
    }
}