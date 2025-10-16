package com.istanbul.qurio.service

import com.istanbul.qurio.repository.dto.QuizResponse
import com.istanbul.qurio.ui.play.PlayPresenter.Companion.MAX_NUM_OF_QUESTIONS
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaService {
    @GET("api.php")
    suspend fun getQuiz(
        @Query("difficulty") difficulty: String,
        @Query("category") category: Int,
        @Query("amount") amount: Int = MAX_NUM_OF_QUESTIONS,
        @Query("type") type: String = "multiple"
    ): QuizResponse
}