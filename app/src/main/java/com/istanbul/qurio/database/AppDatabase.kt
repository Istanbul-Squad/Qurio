package com.istanbul.qurio.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.istanbul.qurio.model.QuizResult
import com.istanbul.qurio.model.UserEntity
import com.istanbul.qurio.model.UserStatisticsEntity
import com.istanbul.qurio.model.Player

@Database(
    entities = [
        QuizResult::class,
        UserStatisticsEntity::class,
        UserEntity::class,
        Player::class
    ],
    version = 3,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao
    abstract fun playerDao(): PlayerDao
    abstract fun userStatisticsDao(): UserStatisticsDao
    abstract fun userDao(): UserDao
}
