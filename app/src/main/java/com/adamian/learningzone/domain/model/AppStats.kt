package com.adamian.learningzone.domain.model

data class AppStats(
    val completionPercentage: Double,
    val totalQuestions: Int,
    val totalAnswers: Int,
    val totalCorrectAnswers: Int,
    val totalWrongAnswers: Int
)
