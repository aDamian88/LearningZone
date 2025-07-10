package com.adamian.learningzone.data.local

import androidx.room.Entity

@Entity(
    primaryKeys = ["quizId", "questionId"]
)
data class QuizQuestionCrossRef(
    val quizId: Int,
    val questionId: Int,
    val userAnswer: String? = null,
    val isCorrect: Boolean? = null
)
