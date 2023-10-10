package com.adamian.learningzone.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adamian.learningzone.data.local.QuestionEntity

@Database(entities = [QuestionEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao
}