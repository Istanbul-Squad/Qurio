package com.istanbul.qurio.repository

import com.istanbul.qurio.database.PlayerDao
import com.istanbul.qurio.database.QuizDao
import com.istanbul.qurio.model.Quiz
import com.istanbul.qurio.model.QuizResult
import com.istanbul.qurio.model.Player
import com.istanbul.qurio.service.TriviaService
import javax.inject.Inject

class TriviaRepositoryImpl @Inject constructor(
    private val triviaService: TriviaService,
    private val quizResultDao: QuizDao,
    private val playerDao: PlayerDao
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
    }

    override suspend fun getPlayer(): Player? {
      return playerDao.getPlayer()
    }

    override suspend fun getPlayerOrCreate(): Player {
        var player = playerDao.getPlayer()
        if (player == null) {
            player = Player(coins = 100, lives = 5)
            playerDao.savePlayer(player)
        }
        return player
    }

    override suspend fun savePlayer(player: Player) {
        playerDao.savePlayer(player)
    }

    override suspend fun buyLife() {
        playerDao.buyLife()
    }

    override suspend fun loseLife() {
        playerDao.loseLife()
    }

    override suspend fun getNumberOfLife(): Int {
      return playerDao.getNumberOfLife()
    }

}