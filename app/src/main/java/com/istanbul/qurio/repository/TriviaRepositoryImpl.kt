package com.istanbul.qurio.repository

import com.istanbul.qurio.database.QuizDao
import com.istanbul.qurio.model.Quiz
import com.istanbul.qurio.model.QuizResult
import com.istanbul.qurio.service.TriviaService
import javax.inject.Inject

class TriviaRepositoryImpl @Inject constructor(
    private val triviaService: TriviaService,
    private val quizResultDao: QuizDao
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
}