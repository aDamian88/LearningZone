package com.adamian.learningzone.model

import com.adamian.learningzone.data.QuestionEntity


data class QuestionItem(
    val question: String,
    val answerDescription: String,
    val options: List<String?>,
    val correctOption: String
) {
    companion object {
        fun mapToEntity(questionItem: QuestionItem): QuestionEntity {
            return QuestionEntity(
                question = questionItem.question,
                answerDescription = questionItem.answerDescription,
                firstOption = questionItem.options[0],
                secondOption = questionItem.options[1],
                thirdOption = questionItem.options[2],
                fourthOption = questionItem.options[3],
                correctOption = questionItem.correctOption
            )
        }

        fun mapToItem(questionEntity: QuestionEntity): QuestionItem {
            return QuestionItem(
                question = questionEntity.question ?: "",
                answerDescription = questionEntity.answerDescription ?: "",
                options = listOf(questionEntity.firstOption,questionEntity.secondOption,questionEntity.thirdOption,questionEntity.fourthOption),
                correctOption = questionEntity.correctOption ?: ""

            )
        }
    }
}
