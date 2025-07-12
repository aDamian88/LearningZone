package com.adamian.learningzone.domain.usecase

import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.domain.repository.QuestionRepository
import com.adamian.learningzone.domain.repository.QuizRepository
import javax.inject.Inject

class GetQuestionsUC @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val quizRepository: QuizRepository
) {
    suspend operator fun invoke(chapterId: Int): List<QuestionItem> {
        val questions = questionRepository.getQuestionsByChapter(chapterId)
        val quizzes = quizRepository.getQuizzesByChapter(chapterId)
        val crossRefs = quizRepository.getAllQuizQuestionCrossRefs()

        val chapterQuizIds = quizzes.map { it.id }.toSet()
        val filteredRefs = crossRefs.filter { it.quizId in chapterQuizIds }

        val questionToQuizId = filteredRefs
            .associateBy { it.questionId }
            .mapValues { it.value.quizId }

        return questions
            .filter { (it.answered ?: 0) == 0 }
            .take(10)
            .map { question ->
                QuestionItem(
                    id = question.id,
                    title = question.title.orEmpty(),
                    question = question.question.orEmpty(),
                    answerDescription = question.answerDescription.orEmpty(),
                    options = listOfNotNull(
                        question.firstOption,
                        question.secondOption,
                        question.thirdOption,
                        question.fourthOption
                    ),
                    correctOption = question.correctOption.orEmpty(),
                    chapter = question.chapter ?: 0,
                    level = question.level ?: 0,
                    right = question.right,
                    wrong = question.wrong,
                    answered = question.answered ?: 0,
                    quizId = questionToQuizId[question.id] ?: 0// nullable, οπότε έχεις και fallback αν θες
                )
            }
    }
}
