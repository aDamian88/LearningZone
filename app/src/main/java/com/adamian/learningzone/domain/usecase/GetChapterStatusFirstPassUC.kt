package com.adamian.learningzone.domain.usecase

import com.adamian.learningzone.domain.repository.QuestionRepository
import com.adamian.learningzone.domain.repository.QuizRepository
import javax.inject.Inject

class GetChapterStatusFirstPassUC @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val quizRepository: QuizRepository
) {

    data class ChapterStats(
        val chapterId: Int,
        val totalProgress: Float,
        val totalQuestions: Int,
        val answeredQuestions: Int,
        val totalQuizzes: Int,
        val completedQuizzes: Int
    )

    suspend operator fun invoke(): List<ChapterStats> {
        val questions = questionRepository.getAllQuestions()
        val quizzes = quizRepository.getAllQuiz()

        // Group questions by chapter
        val questionsByChapter = questions
            .filter { it.chapter != null }
            .groupBy { it.chapter!! }

        // Group quizzes by chapterId
        val quizzesByChapter = quizzes.groupBy { it.chapterId }

        return questionsByChapter.map { (chapterId, chapterQuestions) ->
            val totalQuestions = chapterQuestions.size
            val answeredQuestions = chapterQuestions.count { (it.answered ?: 0) > 0 }
            val progress = if (totalQuestions == 0) 0f else answeredQuestions.toFloat() / totalQuestions

            val chapterQuizzes = quizzesByChapter[chapterId].orEmpty()
            val totalQuizzes = chapterQuizzes.size
            val completedQuizzes = chapterQuizzes.count { it.completed }

            ChapterStats(
                chapterId = chapterId,
                totalQuestions = totalQuestions,
                answeredQuestions = answeredQuestions,
                totalProgress = progress,
                totalQuizzes = totalQuizzes,
                completedQuizzes = completedQuizzes
            )
        }
    }


}