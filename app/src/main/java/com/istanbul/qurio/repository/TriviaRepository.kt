package com.istanbul.qurio.repository

import com.istanbul.qurio.model.Quiz
import com.istanbul.qurio.model.QuizResult


interface TriviaRepository {
    suspend fun getQuiz(
        category: Int,
        difficulty: String,
    ): Quiz

    suspend fun insertQuizResult(quizResult: QuizResult)
}