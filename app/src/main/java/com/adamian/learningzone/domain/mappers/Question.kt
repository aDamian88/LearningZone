package com.adamian.learningzone.domain.mappers

import com.adamian.learningzone.data.local.QuestionEntity
import com.adamian.learningzone.domain.model.QuestionItem

internal fun QuestionItem.toQuestionEntity(): QuestionEntity {
    return QuestionEntity(
        title = title,
        question = question,
        answerDescription = answerDescription,
        firstOption = options[0],
        secondOption = options[1],
        thirdOption = options[2],
        fourthOption = options[3],
        correctOption = correctOption,
        chapter = chapter,
        level = level,
        right = right,
        wrong = wrong,
        answered = answered
    )
}

internal fun QuestionEntity.toQuestionItem(): QuestionItem {
    return QuestionItem(
        id = id,
        title = title ?: "",
        question = question ?: "",
        answerDescription = answerDescription ?: "",
        options = listOf(firstOption, secondOption, thirdOption, fourthOption),
        correctOption = correctOption ?: "",
        chapter = chapter ?: 0,
        level = level ?: 0,
        right = right ?: 0,
        wrong = wrong ?: 0,
        answered = answered ?: 0
    )
}
