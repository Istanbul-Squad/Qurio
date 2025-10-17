package com.istanbul.qurio.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.istanbul.qurio.model.QuizResult
import com.istanbul.qurio.model.Player

@Database(entities = [QuizResult::class, Player::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao
    abstract fun playerDao(): PlayerDao
}