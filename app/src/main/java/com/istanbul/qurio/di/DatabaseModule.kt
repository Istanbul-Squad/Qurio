package com.istanbul.qurio.di

import android.app.Application
import androidx.room.Room
import com.istanbul.qurio.database.AppDatabase
import com.istanbul.qurio.database.PlayerDao
import com.istanbul.qurio.database.QuizDao
import com.istanbul.qurio.database.UserDao
import com.istanbul.qurio.database.UserStatisticsDao
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

    @Provides
    @Singleton
    fun providePlayerDao(database: AppDatabase): PlayerDao {
        return database.playerDao()
    }

    @Provides
    fun provideUserStatisticsDao(database: AppDatabase): UserStatisticsDao = database.userStatisticsDao()

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()
}