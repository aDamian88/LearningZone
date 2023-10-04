package com.adamian.learningzone.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adamian.learningzone.data.QuestionEntity

@Database(entities = [QuestionEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao
}