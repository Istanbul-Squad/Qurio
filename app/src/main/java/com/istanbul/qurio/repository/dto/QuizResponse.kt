package com.istanbul.qurio.repository.dto


import com.google.gson.annotations.SerializedName
import com.istanbul.qurio.model.Quiz

data class QuizResponse(
    @SerializedName("response_code")
    val responseCode: Int? = null,
    @SerializedName("results")
    val results: List<QuestionDto>? = null
) {
    fun toDomain(): Quiz {
        return Quiz(
            questions = results?.mapNotNull { it.toDomain() } ?: emptyList()
        )
    }
}