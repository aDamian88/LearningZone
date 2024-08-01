package com.adamian.learningzone.data.repository

import com.adamian.learningzone.data.database.AppDao
import com.adamian.learningzone.data.local.QuestionEntity
import com.adamian.learningzone.domain.mappers.toQuestionItem
import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.domain.repository.QuestionRepository
import javax.inject.Inject

class QuestionRepositoryImp @Inject constructor(
    private val appDao: AppDao
): QuestionRepository {

    override suspend fun saveQuestion(questionEntity: QuestionEntity) {
        appDao.insertQuestion(questionEntity)
    }

    override suspend fun getAllQuestions(): List<QuestionItem> {
        return appDao.getAllQuestions().map {
            it.toQuestionItem() // perhaps this should go to a different layer.
        }
    }

    override suspend fun insertAll(vararg questions: QuestionEntity) {
        appDao.insertAll(*questions)
    }

    override suspend fun isDatabaseEmpty(): Boolean {
        return appDao.getRowCount() == 0
    }

}
