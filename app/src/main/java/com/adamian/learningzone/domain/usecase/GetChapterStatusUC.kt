package com.adamian.learningzone.domain.usecase

import com.adamian.learningzone.domain.repository.QuestionRepository
import javax.inject.Inject

class GetChapterStatusUC @Inject constructor(
    private val repository: QuestionRepository
) {

    suspend operator fun invoke(): Map<Int, Int> {
        // Get all questions from the repository
        val allQuestions = repository.getAllQuestions()

        // Group questions by chapter, filter out null chapter IDs
        val questionsByChapter = allQuestions.groupBy { it.chapter }
            .filterKeys { it != null } // Filter out null chapterIds

        // Calculate the learning percentage for each chapter
        val chapterProgressMap = mutableMapOf<Int, Int>()
        for ((chapterId, questions) in questionsByChapter) {
            chapterId?.let { // Ensure non-null chapterId
                val learnedQuestionsCount = questions.count { it.isLearned() }
                val totalQuestionsCount = questions.size
                val learningPercentage = if (totalQuestionsCount > 0) {
                    ((learnedQuestionsCount / totalQuestionsCount.toFloat()) * 100).toInt()
                } else {
                    0 // If no questions are found, return 0% progress
                }
                chapterProgressMap[chapterId] = learningPercentage
            }
        }

        return chapterProgressMap
    }
}