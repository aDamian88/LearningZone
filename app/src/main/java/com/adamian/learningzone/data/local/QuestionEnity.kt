package com.adamian.learningzone.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.math.pow

@Entity
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "question") val question: String?,
    @ColumnInfo(name = "answerDescription") val answerDescription: String?,
    @ColumnInfo(name = "firstOption") val firstOption: String?,
    @ColumnInfo(name = "secondOption") val secondOption: String?,
    @ColumnInfo(name = "thirdOption") val thirdOption: String?,
    @ColumnInfo(name = "fourthOption") val fourthOption: String?,
    @ColumnInfo(name = "correctOption") val correctOption: String?,
    @ColumnInfo(name = "chapter") val chapter: Int?,
    @ColumnInfo(name = "level") val level: Int?,
    @ColumnInfo(name = "right") val right: Int = 0,
    @ColumnInfo(name = "wrong") val wrong: Int = 0,
    @ColumnInfo(name = "answered") val answered: Int?,
) {

    fun isLearned(): Boolean {

        // Parameters for the learning formula
        val rightAnswerWeight = 1.0
        val wrongAnswerWeight = 0.5
        val decayFactor = 0.9 // Decay factor to reduce the impact of older mistakes

        // Minimum score to consider a question 'learned'
        val learningThreshold = 2.0
        // Apply an exponential decay formula to calculate the effective number of wrong answers
        val effectiveWrong = wrong * wrongAnswerWeight * decayFactor.pow(right.toDouble())

        // Calculate the weighted score
        val score = (right * rightAnswerWeight) - effectiveWrong

        // Determine if the question is learned based on the threshold
        return score >= learningThreshold
    }
}