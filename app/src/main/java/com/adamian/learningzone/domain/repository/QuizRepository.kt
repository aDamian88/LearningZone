package com.adamian.learningzone.domain.repository

import com.adamian.learningzone.data.local.QuizEntity
import com.adamian.learningzone.data.local.QuizQuestionCrossRef

interface QuizRepository {
    suspend fun insertQuiz(quiz: QuizEntity): Int
    suspend fun insertQuizQuestions(quizId: Int, questionIds: List<Int>)
    suspend fun getAllQuiz(): List<QuizEntity>
    suspend fun getQuizzesByChapter(chapterId: Int): List<QuizEntity>
    suspend fun getAllQuizQuestionCrossRefs(): List<QuizQuestionCrossRef>
    suspend fun completeQuiz(quizId: Int)

//    suspend fun getQuizWithQuestions(quizId: Int): QuizWithQuestions
}