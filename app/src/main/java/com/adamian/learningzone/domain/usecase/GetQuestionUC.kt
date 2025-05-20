package com.adamian.learningzone.domain.usecase

import com.adamian.learningzone.domain.model.QuestionItem
import com.adamian.learningzone.domain.repository.QuestionRepository
import javax.inject.Inject

class GetQuestionsUC @Inject constructor(
    private val repository: QuestionRepository
) {
    suspend operator fun invoke(chapterId: Int): List<QuestionItem> {
        return repository.getQuestionsByChapter(chapterId)
            .map { questionEntity ->
            QuestionItem( // Todo perhaps create a mapper here
                id = questionEntity.id,
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
                right = questionEntity.right,
                wrong = questionEntity.wrong,
                answered = questionEntity.answered ?:0
            )
        }.filter { it.isAnswered().not() }.take(10)
    }
}
