package com.adamian.learningzone.domain.usecase

import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.domain.repository.QuestionRepository
import javax.inject.Inject

class GetQuestionsUC @Inject constructor(
    private val repository: QuestionRepository
) {
    suspend operator fun invoke(): List<QuestionItem> {
        return repository.getAllQuestions()
            .map { questionEntity ->
            QuestionItem(
                title = questionEntity.title ?: "",
                question = questionEntity.question ?: "",
                answerDescription = questionEntity.answerDescription ?: "",
                options = listOfNotNull(
                    questionEntity.firstOption,
                    questionEntity.secondOption,
                    questionEntity.thirdOption,
                    questionEntity.fourthOption
                ),
                correctOption = questionEntity.correctOption ?: "",
                chapter = questionEntity.chapter ?: 0,
                level = questionEntity.level ?: 0,
                right = questionEntity.right ?:0,
                wrong = questionEntity.wrong ?:0,
                answered = questionEntity.answered ?:0
            )
        }
    }
}
