package com.istanbul.qurio.repository

import com.istanbul.qurio.database.QuizDao
import com.istanbul.qurio.database.UserDao
import com.istanbul.qurio.database.UserStatisticsDao
import com.istanbul.qurio.model.Quiz
import com.istanbul.qurio.model.QuizResult
import com.istanbul.qurio.service.TriviaService
import javax.inject.Inject
import kotlin.collections.map
import com.istanbul.qurio.model.GameCategory
import com.istanbul.qurio.model.UserEntity
import com.istanbul.qurio.model.UserStatisticsEntity

class TriviaRepositoryImpl @Inject constructor(
    private val triviaService: TriviaService,
    private val quizResultDao: QuizDao,
    private val userStatisticsDao: UserStatisticsDao,
    private val userDao: UserDao
) : TriviaRepository {
    override suspend fun getQuiz(
        category: Int,
        difficulty: String
    ): Quiz {
        // TODO: add error handling for exceptions and status codes
        return triviaService.getQuiz(
            difficulty = difficulty,
            category = category
        ).toDomain()
    }

    override suspend fun insertQuizResult(quizResult: QuizResult) {
        quizResultDao.insertResult(quizResult)

        val stats = userStatisticsDao.getUserStatistics()
            ?: UserStatisticsEntity().also { userStatisticsDao.insertOrUpdate(it) }

        val earnedPoints = quizResult.correctAnswers * 10

        val updatedStats = stats.copy(
            points = stats.points + earnedPoints,
            streakDays = stats.streakDays + 1
        )

        userStatisticsDao.insertOrUpdate(updatedStats)
    }

    override suspend fun getGames(): List<GameCategory> {
        return triviaService.getGames().categories.map { it.toDomain() }
    }

    override  suspend fun getUserStatistics(): UserStatisticsEntity {
        return userStatisticsDao.getUserStatistics()
            ?: UserStatisticsEntity().also {
                userStatisticsDao.insertOrUpdate(it)
            }
    }

    override suspend fun updateUserStatistics(statistics: UserStatisticsEntity) {
        userStatisticsDao.insertOrUpdate(statistics)
    }

    override suspend fun getUser(): UserEntity {
        return userDao.getUser() ?: UserEntity().also { userDao.insertOrUpdate(it) }
    }

    override suspend fun updateUser(user: UserEntity) {
        userDao.insertOrUpdate(user)
    }

    override suspend fun getAllResults(): List<QuizResult> {
        return quizResultDao.getAllResults()
    }
}