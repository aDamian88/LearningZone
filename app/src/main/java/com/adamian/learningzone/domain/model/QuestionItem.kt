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
    // Parameters for the learning formula
    private val rightAnswerWeight = 1.0
    private val wrongAnswerWeight = 0.5
    private val decayFactor = 0.9 // Decay factor to reduce the impact of older mistakes

    // Minimum score to consider a question 'learned'
    private val learningThreshold = 2.0

    fun isLearned(): Boolean {
        // Apply an exponential decay formula to calculate the effective number of wrong answers
        val effectiveWrong = wrong * wrongAnswerWeight * Math.pow(decayFactor, right.toDouble())

        // Calculate the weighted score
        val score = (right * rightAnswerWeight) - effectiveWrong

        // Determine if the question is learned based on the threshold
        return score >= learningThreshold
    }
}

