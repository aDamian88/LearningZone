package com.adamian.learningzone.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "question") val question: String?,
    @ColumnInfo(name = "answerDescription") val answerDescription: String?
) {
}