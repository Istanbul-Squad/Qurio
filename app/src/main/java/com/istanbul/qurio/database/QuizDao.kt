package com.istanbul.qurio.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.istanbul.qurio.model.QuizResult

@Dao
interface QuizDao {
    @Insert
    suspend fun insertResult(result: QuizResult)

    @Query("SELECT * FROM quiz_results ORDER BY timestamp DESC")
    suspend fun getAllResults(): List<QuizResult>
}