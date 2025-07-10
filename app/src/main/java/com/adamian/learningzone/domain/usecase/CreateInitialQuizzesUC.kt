package com.adamian.learningzone.domain.usecase

import android.util.Log
import com.adamian.learningzone.data.local.QuestionEntity
import com.adamian.learningzone.data.local.QuizEntity
import com.adamian.learningzone.data.local.QuizQuestionCrossRef
import com.adamian.learningzone.domain.repository.QuizRepository
import javax.inject.Inject

class CreateInitialQuizzesUC @Inject constructor(
    private val quizRepository: QuizRepository
) {

    private val TAG = "CreateInitialQuizzesUC"
    suspend fun createQuizzes(questions: List<QuestionEntity>) {
        Log.d(TAG, "createQuizzes: questions: ${questions.size}")
        val firstChapter = questions.filter { it.chapter == 1 }
        Log.d(TAG, "createQuizzes: firstChapter: ${firstChapter.size}")
        val grouped = questions.groupBy { it.chapter }

        grouped.forEach { (chapter, questionsInChapter) ->
            val chunks = questionsInChapter.chunked(10)
            Log.d(TAG, "createQuizzes: chapter: ${chapter}")
            Log.d(TAG, "createQuizzes: questionsInChapter: ${questionsInChapter.size}")
            chunks.forEach { chunk ->
                Log.d(TAG, "createQuizzes: chunk: ${chunk.size}")

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
//        grouped.forEach { group ->
//            Log.d(TAG, "createQuizzes: groupIndex: ${group.key}")
//            Log.d(TAG, "createQuizzes: groupValueSize: ${group.value.size}")
//            Log.d(TAG, "createQuizzes: group: ${group}")
//            val quizChunks: List<QuestionEntity> = group.value.take(10) // todo take only the unanswered ?
//            Log.d(TAG, "createQuizzes: quizChunks.size: ${quizChunks.size} group: ${group.value[0].chapter}")
//
//            val quizId = quizRepository.insertQuiz(
//                QuizEntity(
//                    chapterId = group.key ?: 0,
//                    completed = false
//                )
//            )
//            Log.d(TAG, "createQuizzes: chunks.map: ${quizChunks.map { it.id }}")
//            quizRepository.insertQuizQuestions(quizId, quizChunks.map { it.id })
//        }
    }
}
