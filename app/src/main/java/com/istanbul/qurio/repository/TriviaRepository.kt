package com.istanbul.qurio.repository

import com.istanbul.qurio.model.GameCategory
import com.istanbul.qurio.model.Quiz
import com.istanbul.qurio.model.QuizResult
import com.istanbul.qurio.model.UserEntity
import com.istanbul.qurio.model.UserStatisticsEntity
import com.istanbul.qurio.model.Player


interface TriviaRepository {
    suspend fun getQuiz(
        category: Int,
        difficulty: String,
    ): Quiz

    suspend fun insertQuizResult(quizResult: QuizResult)

    suspend fun getGames(): List<GameCategory>

    suspend fun getUserStatistics(): UserStatisticsEntity

    suspend fun updateUserStatistics(statistics: UserStatisticsEntity)

    suspend fun getUser(): UserEntity

    suspend fun updateUser(user: UserEntity)

    suspend fun getAllResults(): List<QuizResult>

    suspend fun getPlayer(): Player?
    suspend fun getPlayerOrCreate(): Player
    suspend fun savePlayer(player: Player)
    suspend fun buyLife()
    suspend fun loseLife()
    suspend fun getNumberOfLife(): Int
}