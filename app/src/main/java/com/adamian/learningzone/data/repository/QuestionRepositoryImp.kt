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

    suspend fun getAllQuestionItems(): List<QuestionItem> {
        return appDao.getAllQuestions().map {
            it.toQuestionItem() // perhaps this should go to a different layer.
        }
    }

    override suspend fun getAllQuestions(): List<QuestionEntity> {
        return appDao.getAllQuestions()
    }

    override suspend fun getQuestionsByChapter(chapterId: Int): List<QuestionEntity> {
        return appDao.getQuestionsByChapter(chapterId)
    }

    override suspend fun insertAll(vararg questions: QuestionEntity) {
        appDao.insertAll(*questions)
    }

    override suspend fun isDatabaseEmpty(): Boolean {
        return appDao.getRowCount() == 0
    }

    override suspend fun incrementRight(questionId: Int) {
        appDao.incrementRight(questionId)
    }

    override suspend fun incrementWrong(questionId: Int) {
        appDao.incrementWrong(questionId)
    }

    override suspend fun incrementAnswered(questionId: Int) {
        appDao.incrementAnswered(questionId)  // Call DAO method to increment 'answered'
    }

}
