package com.adamian.learningzone.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.adamian.learningzone.data.QuestionEntity

@Dao
interface AppDao {
    @Insert
    suspend fun insertQuestion(questionEntity: QuestionEntity)

    @Query("SELECT * FROM QuestionEntity")
    suspend fun getAll(): List<QuestionEntity>
}