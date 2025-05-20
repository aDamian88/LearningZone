package com.adamian.learningzone.domain.usecase

import com.adamian.learningzone.domain.model.AppStats
import com.adamian.learningzone.domain.repository.QuestionRepository
import javax.inject.Inject

class GetAppStatsUC @Inject constructor(
    private val repository: QuestionRepository
) {

    suspend operator fun invoke(): AppStats {
        // Get all questions from the repository
        val allQuestions = repository.getAllQuestions()

        // Calculate the total number of questions
        val totalQuestions = allQuestions.size

        // Calculate the number of learned questions
        val answeredQuestions = allQuestions.count { it.answered != 0}

        // Calculate the completion percentage based on learned questions
        val completionPercentage = if (totalQuestions > 0) {
            answeredQuestions.toDouble() / totalQuestions
        } else {
            0.0
        }

        // Calculate the total number of answers
        val totalAnswers = allQuestions.sumOf { it.answered ?: 0 }

        // Calculate the total number of correct answers
        val totalCorrectAnswers = allQuestions.sumOf { it.right ?: 0 }

        // Calculate the total number of wrong answers
        val totalWrongAnswers = allQuestions.sumOf { it.wrong ?: 0 }

        // Return the stats
        return AppStats(
            completionPercentage = completionPercentage,
            totalQuestions = totalQuestions,
            totalAnswers = totalAnswers,
            totalCorrectAnswers = totalCorrectAnswers,
            totalWrongAnswers = totalWrongAnswers
        )
    }
}
