package com.istanbul.qurio.repository.dto


import com.google.gson.annotations.SerializedName
import com.istanbul.qurio.model.Answer
import com.istanbul.qurio.model.Question

data class QuestionDto(
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("difficulty")
    val difficulty: String? = null,
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("question")
    val question: String? = null,
    @SerializedName("correct_answer")
    val correctAnswer: String? = null,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>? = null
) {
    fun toDomain(): Question? {
        if (question == null || correctAnswer == null || incorrectAnswers == null) return null
        val answers = listOf(Answer(text = correctAnswer, isCorrect = true)) + incorrectAnswers.map { Answer(text = it, isCorrect = false) }

        return Question(
            question = question,
            answers = answers.shuffled()
        )
    }
}