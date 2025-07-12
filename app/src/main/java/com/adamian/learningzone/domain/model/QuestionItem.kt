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
    val answered: Int,
    val quizId: Int
) {
    private val rightAnswerWeight = 1.0
    private val wrongAnswerWeight = 0.5
    private val decayFactor = 0.9

    private val learningThreshold = 2.0

    fun isLearned(): Boolean {
        val effectiveWrong = wrong * wrongAnswerWeight * Math.pow(decayFactor, right.toDouble())
        val score = (right * rightAnswerWeight) - effectiveWrong
        return score >= learningThreshold
    }

    fun isAnswered(): Boolean {
        return answered != 0
    } // todo re-check if its necessary
}

