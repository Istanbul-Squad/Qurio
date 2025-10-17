package com.istanbul.qurio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    val name: String = "Guest",
    val profileImageUrl: String = "",
)


@Entity(tableName = "user_statistics")
data class UserStatisticsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val points: Int = 0,
    val lives: Int = 3,
    val trophies: Int = 0,
    val streakDays: Int = 0
)

