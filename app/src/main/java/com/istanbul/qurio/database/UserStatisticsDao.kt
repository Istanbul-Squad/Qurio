package com.istanbul.qurio.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.istanbul.qurio.model.UserStatisticsEntity

@Dao
interface UserStatisticsDao {

    @Query("SELECT * FROM user_statistics LIMIT 1")
    suspend fun getUserStatistics(): UserStatisticsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(statistics: UserStatisticsEntity)

    @Query("UPDATE user_statistics SET points = :points, lives = :lives, trophies = :trophies, streakDays = :streakDays WHERE id = :id")
    suspend fun updateStatistics(id: Int, points: Int, lives: Int, trophies: Int, streakDays: Int)
}