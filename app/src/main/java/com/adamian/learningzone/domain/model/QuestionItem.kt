package com.adamian.learningzone.domain.model


data class QuestionItem(
    val title: String,
    val question: String,
    val answerDescription: String,
    val options: List<String?>,
    val correctOption: String,
    val chapter: Int,
    val level: Int
)
