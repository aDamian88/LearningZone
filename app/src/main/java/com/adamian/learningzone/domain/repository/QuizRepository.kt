package com.adamian.learningzone.domain.repository

import com.adamian.learningzone.data.local.QuizEntity

interface QuizRepository {
    suspend fun insertQuiz(quiz: QuizEntity): Int
    suspend fun insertQuizQuestions(quizId: Int, questionIds: List<Int>)
//    suspend fun getQuizWithQuestions(quizId: Int): QuizWithQuestions
}