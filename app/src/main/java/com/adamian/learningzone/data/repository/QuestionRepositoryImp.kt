package com.adamian.learningzone.data.repository

import android.util.Log
import com.adamian.learningzone.data.database.AppDao
import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.domain.model.QuestionItem.Companion.mapToEntity
import com.adamian.learningzone.domain.model.QuestionItem.Companion.mapToItem
import com.adamian.learningzone.domain.repository.QuestionRepository
import javax.inject.Inject

class QuestionRepositoryImp @Inject constructor(
    private val appDao: AppDao
): QuestionRepository {
    private val TAG = "QuestionRepository"
    override suspend fun saveQuestion(questionItem: QuestionItem) {
        appDao.insertQuestion(mapToEntity(questionItem))
    }

    override suspend fun getAllQuestions(): List<QuestionItem> {
        return appDao.getAllQuestions().map {
            Log.d(TAG, "getAllQuestions: $it")
            mapToItem(it)
        }
    }
}
