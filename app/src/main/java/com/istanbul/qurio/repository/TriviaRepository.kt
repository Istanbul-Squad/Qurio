package com.istanbul.qurio.repository

import com.istanbul.qurio.model.Quiz


interface TriviaRepository {
    suspend fun getQuiz(
        category: Int,
        difficulty: String,
    ): Quiz
}