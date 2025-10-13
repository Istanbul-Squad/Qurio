package com.istanbul.qurio.repository

import com.istanbul.qurio.model.Quiz
import com.istanbul.qurio.service.TriviaService
import javax.inject.Inject

class TriviaRepositoryImpl @Inject constructor(
    private val triviaService: TriviaService
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
}