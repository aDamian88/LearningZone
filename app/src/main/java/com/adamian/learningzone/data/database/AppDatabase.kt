package com.adamian.learningzone.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adamian.learningzone.data.local.QuestionEntity
import com.adamian.learningzone.data.local.QuizEntity
import com.adamian.learningzone.data.local.QuizQuestionCrossRef

@Database(entities = [QuestionEntity::class, QuizEntity::class, QuizQuestionCrossRef::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao
}