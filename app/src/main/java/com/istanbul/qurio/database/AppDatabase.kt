package com.istanbul.qurio.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.istanbul.qurio.model.QuizResult

@Database(entities = [QuizResult::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao
}