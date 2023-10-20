package com.adamian.learningzone.domain.mappers

import com.adamian.learningzone.data.local.QuestionEntity
import com.adamian.learningzone.domain.model.QuestionItem

internal fun QuestionItem.toQuestionEntity(): QuestionEntity {
    return QuestionEntity(
        question = question,
        answerDescription = answerDescription,
        firstOption = options[0],
        secondOption = options[1],
        thirdOption = options[2],
        fourthOption = options[3],
        correctOption = correctOption,
        chapter = chapter
    )
}

internal fun QuestionEntity.toQuestionItem(): QuestionItem {
    return QuestionItem(
        question = question ?: "",
        answerDescription = answerDescription ?: "",
        options = listOf(firstOption, secondOption, thirdOption, fourthOption),
        correctOption = correctOption ?: "",
        chapter = chapter ?: 0
    )
}
