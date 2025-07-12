package com.adamian.learningzone.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adamian.learningzone.data.local.QuestionEntity
import com.adamian.learningzone.data.local.QuizEntity
import com.adamian.learningzone.data.local.QuizQuestionCrossRef

@Dao
interface AppDao {

    // questions
    @Insert
    suspend fun insertQuestion(questionEntity: QuestionEntity)

    @Query("SELECT * FROM QuestionEntity")
    suspend fun getAllQuestions(): List<QuestionEntity>

    @Query("SELECT * FROM QuestionEntity WHERE chapter = :chapterId")
    suspend fun getQuestionsByChapter(chapterId: Int): List<QuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg questions: QuestionEntity)

    @Query("SELECT COUNT(*) FROM QuestionEntity")
    suspend fun getRowCount(): Int

    @Query("UPDATE QuestionEntity SET right = right + 1, answered = answered + 1 WHERE id = :questionId")
    suspend fun incrementRight(questionId: Int)

    @Query("UPDATE QuestionEntity SET wrong = wrong + 1, answered = answered + 1 WHERE id = :questionId")
    suspend fun incrementWrong(questionId: Int)

    // quiz
    @Insert
    suspend fun insertQuiz(quiz: QuizEntity): Long

    @Insert
    suspend fun insertQuizQuestions(crossRefs: List<QuizQuestionCrossRef>)

    @Query("SELECT * FROM quiz")
    suspend fun getAllQuiz(): List<QuizEntity>

    @Query("SELECT * FROM quiz WHERE chapterId = :chapterId")
    suspend fun getQuizzesByChapter(chapterId: Int): List<QuizEntity>

    @Query("SELECT * FROM QuizQuestionCrossRef")
    suspend fun getAllQuizQuestionCrossRefs(): List<QuizQuestionCrossRef>


    @Query("UPDATE quiz SET completed = completed + 1 WHERE id = :quizId")
    suspend fun completeQuiz(quizId: Int)

}