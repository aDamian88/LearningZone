package com.adamian.learningzone.domain.usecase

import com.adamian.learningzone.data.local.QuestionEntity
import com.adamian.learningzone.data.local.QuizEntity
import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.domain.repository.QuestionRepository
import com.adamian.learningzone.domain.repository.QuizRepository
import javax.inject.Inject
import kotlin.math.max
import kotlin.random.Random

class GetRecapQuestionsUC @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val quizRepository: QuizRepository
) {

    suspend operator fun invoke(
        chapterId: Int,
        limit: Int = 10
    ): List<QuestionItem> {
        val pool = questionRepository
            .getQuestionsByChapter(chapterId)

        if (pool.isEmpty()) return emptyList()

        val picked = weightedPickWithoutReplacement(
            items = pool,
            k = minOf(limit, pool.size),
            weightOf = ::recapWeight
        )

        val quizId = quizRepository.insertQuiz(QuizEntity(chapterId = chapterId))

        quizRepository.insertQuizQuestions(
            quizId = quizId,
            questionIds = picked.map { it.id }
        )

        return picked.map { q ->
            QuestionItem(
                id = q.id,
                title = q.title.orEmpty(),
                question = q.question.orEmpty(),
                answerDescription = q.answerDescription.orEmpty(),
                options = listOfNotNull(q.firstOption, q.secondOption, q.thirdOption, q.fourthOption),
                correctOption = q.correctOption.orEmpty(),
                chapter = q.chapter ?: 0,
                level = q.level ?: 0,
                right = q.right,
                wrong = q.wrong,
                answered = q.answered ?: 0,
                quizId = quizId
            )
        }
    }

    private fun recapWeight(q: QuestionEntity): Double {
        val answered = (q.answered ?: 1).coerceAtLeast(1)
        val wrong = q.wrong.coerceAtLeast(0)
        val right = q.right.coerceAtLeast(0)

        val wrongRate = wrong.toDouble() / answered.toDouble()

        val score =
            (wrong * 3.0) +
                    (wrongRate * 10.0) -
                    (right * 0.5) -
                    (answered * 0.1)

        return 1.0 + max(0.0, score)
    }

    private fun <T> weightedPickWithoutReplacement(
        items: List<T>,
        k: Int,
        weightOf: (T) -> Double
    ): List<T> {
        if (k <= 0 || items.isEmpty()) return emptyList()

        val remaining = items.toMutableList()
        val picked = ArrayList<T>(k)

        repeat(minOf(k, remaining.size)) {
            val weights = remaining.map { max(0.0, weightOf(it)) }
            val total = weights.sum()

            val chosenIndex =
                if (total <= 0.0) {
                    Random.Default.nextInt(remaining.size)
                } else {
                    var r = Random.Default.nextDouble(total)
                    var idx = 0
                    for (i in remaining.indices) {
                        r -= weights[i]
                        if (r <= 0.0) {
                            idx = i
                            break
                        }
                    }
                    idx
                }

            picked.add(remaining.removeAt(chosenIndex))
        }

        return picked
    }
}