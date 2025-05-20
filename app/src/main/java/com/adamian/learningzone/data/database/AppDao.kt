package com.adamian.learningzone.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adamian.learningzone.data.local.QuestionEntity

@Dao
interface AppDao {
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

    @Query("UPDATE QuestionEntity SET answered = answered + 1 WHERE id = :questionId")
    suspend fun incrementAnswered(questionId: Int)
}