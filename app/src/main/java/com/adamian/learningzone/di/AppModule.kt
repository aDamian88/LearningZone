package com.adamian.learningzone.di

import android.app.Application
import androidx.room.Room
import com.adamian.learningzone.data.database.AppDao
import com.adamian.learningzone.data.database.AppDatabase
import com.adamian.learningzone.data.repository.QuestionRepositoryImp
import com.adamian.learningzone.domain.repository.QuestionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideApplicationDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "learning_zone_db.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideApplicationDao(
        database: AppDatabase
    ): AppDao {
        return database.appDao()
    }

    @Provides
    @Singleton
    fun provideQuestionRepository(
        appDao: AppDao
    ): QuestionRepository {
        return QuestionRepositoryImp(appDao = appDao)
    }

}