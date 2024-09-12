package com.adamian.learningzone.domain.model


data class QuestionItem(
    val id: Int,
    val title: String,
    val question: String,
    val answerDescription: String,
    val options: List<String?>,
    val correctOption: String,
    val chapter: Int,
    val level: Int,
    val right: Int,
    val wrong: Int,
    val answered: Int
) {
    fun isLearned(): Boolean {
        val totalAttempts = right + wrong
        val correctnessRatio = if (totalAttempts > 0) (right.toDouble() / totalAttempts) * 100 else 0.0
        return correctnessRatio >= 80 && totalAttempts >= 3
    }
}

