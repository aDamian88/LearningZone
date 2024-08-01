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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg questions: QuestionEntity)

    @Query("SELECT COUNT(*) FROM QuestionEntity")
    suspend fun getRowCount(): Int

}