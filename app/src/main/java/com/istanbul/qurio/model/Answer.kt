package com.istanbul.qurio.model

data class Answer(
    val text: String,
    val isCorrect: Boolean,
    val choiceStatus: ChoiceStatus = ChoiceStatus.NotSelected
) {
    enum class ChoiceStatus {
        NotSelected,
        Selected,
        Chosen
    }
}
