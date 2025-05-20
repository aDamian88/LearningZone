package com.adamian.learningzone.domain.usecase

import com.adamian.learningzone.domain.repository.QuestionRepository
import javax.inject.Inject

class GetChapterStatusFirstPassUC @Inject constructor(
    private val repository: QuestionRepository
) {

    suspend operator fun invoke(): Map<Int, Int> {
        val allQuestions = repository.getAllQuestions()

        val questionsByChapter = allQuestions.groupBy { it.chapter }
            .filterKeys { it != null }

        val chapterProgressMap = mutableMapOf<Int, Int>()
        for ((chapterId, questions) in questionsByChapter) {
            chapterId?.let {
                val answeredQuestionsCount = questions.count { it.answered != 0 }
                val totalQuestionsCount = questions.size
                val answeredPercentage = if (totalQuestionsCount > 0) {
                    ((answeredQuestionsCount / totalQuestionsCount.toFloat()) * 100).toInt()
                } else {
                    0
                }
                chapterProgressMap[chapterId] = answeredPercentage
            }
        }

        return chapterProgressMap
    }
}