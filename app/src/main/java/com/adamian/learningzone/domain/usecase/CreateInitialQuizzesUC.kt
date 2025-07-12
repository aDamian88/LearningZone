package com.adamian.learningzone.domain.usecase

import com.adamian.learningzone.data.local.QuestionEntity
import com.adamian.learningzone.data.local.QuizEntity
import com.adamian.learningzone.data.local.QuizQuestionCrossRef
import com.adamian.learningzone.domain.repository.QuizRepository
import javax.inject.Inject

class CreateInitialQuizzesUC @Inject constructor(
    private val quizRepository: QuizRepository
) {

    suspend fun createQuizzes(questions: List<QuestionEntity>) {
        val grouped = questions.groupBy { it.chapter }

        grouped.forEach { (chapter, questionsInChapter) ->
            val chunks = questionsInChapter.chunked(10)
            chunks.forEach { chunk ->
                val quizId = quizRepository.insertQuiz(
                    QuizEntity(
                        chapterId = chapter ?: 0
                    )
                )

                val crossRefs = chunk.map { question ->
                    QuizQuestionCrossRef(
                        quizId = quizId,
                        questionId = question.id
                    )
                }

                quizRepository.insertQuizQuestions(quizId, crossRefs.map { it.questionId })
            }
        }
    }
}
