package com.adamian.learningzone.domain.model


data class QuestionItem(
    val question: String,
    val answerDescription: String,
    val options: List<String?>,
    val correctOption: String
)
