package com.adamian.learningzone.model

import com.adamian.learningzone.data.QuestionEntity


data class QuestionItem(
    val question: String,
    val answerDescription: String,
//    val chapter: String,
//    val choices: ArrayList<String>,
//    val answer: String,
//    val status: String
) {
    companion object {
        fun mapToEntity(questionItem: QuestionItem): QuestionEntity {
            return QuestionEntity(
                question = questionItem.question,
                answerDescription = questionItem.answerDescription
            )

        }

        fun mapToItem(questionEntity: QuestionEntity): QuestionItem {
            return QuestionItem(
                question = questionEntity.question ?: "",
                answerDescription = questionEntity.answerDescription ?: ""
            )
        }
    }
}
