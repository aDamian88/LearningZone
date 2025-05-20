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
)
