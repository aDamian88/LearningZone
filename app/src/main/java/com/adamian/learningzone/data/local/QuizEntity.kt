package com.adamian.learningzone.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz")
data class QuizEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val chapterId: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val completed: Boolean = false
)