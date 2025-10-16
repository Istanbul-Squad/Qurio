package com.istanbul.qurio.di

import android.app.Application
import androidx.room.Room
import com.istanbul.qurio.database.AppDatabase
import com.istanbul.qurio.database.QuizDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule{
    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "qurio_database"
        ).build()
    }

    @Provides
    fun provideQuizDao(database: AppDatabase): QuizDao {
        return database.quizDao()
    }
}