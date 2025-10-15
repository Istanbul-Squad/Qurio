package com.istanbul.qurio.ui.play

import com.istanbul.qurio.model.Answer
import com.istanbul.qurio.model.Question
import com.istanbul.qurio.ui.base.BaseView

interface PlayView: BaseView {
    fun updateCoinsNumber(number: Int)
    fun showQuestion(question: Question, index: Int)
    fun markAnswerCorrect(answer: Answer)
    fun markAnswerWrong(answer: Answer)
    fun markAnswerSkipped(answer: Answer)
    fun markAnswerSelected(answer: Answer)
    fun convertToNextButton()
    fun convertToCheckButton()
    fun goToResult(correctAnswersCount: Int, inCorrectAnswersCount: Int, skippedAnswersCount: Int)
    fun startTimer()
    fun stopTimer()
}
