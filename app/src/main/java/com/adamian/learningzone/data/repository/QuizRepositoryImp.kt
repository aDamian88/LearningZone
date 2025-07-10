package com.adamian.learningzone.data.repository

import com.adamian.learningzone.data.database.AppDao
import com.adamian.learningzone.data.local.QuizEntity
import com.adamian.learningzone.data.local.QuizQuestionCrossRef
import com.adamian.learningzone.domain.repository.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val quizDao: AppDao
) : QuizRepository {

    override suspend fun insertQuiz(quiz: QuizEntity): Int {
        return quizDao.insertQuiz(quiz).toInt()
    }

    override suspend fun insertQuizQuestions(quizId: Int, questionIds: List<Int>) {
        val refs = questionIds.map { QuizQuestionCrossRef(quizId, it) }
        quizDao.insertQuizQuestions(refs)
    }

//    override suspend fun getQuizWithQuestions(quizId: Int): QuizWithQuestions {
//        return quizDao.getQuizWithQuestions(quizId)
//    }
}
