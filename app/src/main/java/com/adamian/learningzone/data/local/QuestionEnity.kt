package com.adamian.learningzone.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    // Parameters for the learning formula
    private val rightAnswerWeight = 1.0
    private val wrongAnswerWeight = 0.5
    private val decayFactor = 0.9 // Decay factor to reduce the impact of older mistakes

    // Minimum score to consider a question 'learned'
    private val learningThreshold = 2.0

    fun isLearned(): Boolean {
        // Apply an exponential decay formula to calculate the effective number of wrong answers
        val effectiveWrong = wrong * wrongAnswerWeight * Math.pow(decayFactor, right.toDouble())

        // Calculate the weighted score
        val score = (right * rightAnswerWeight) - effectiveWrong

        // Determine if the question is learned based on the threshold
        return score >= learningThreshold
    }
}