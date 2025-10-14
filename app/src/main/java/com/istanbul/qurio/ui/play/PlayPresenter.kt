package com.istanbul.qurio.ui.play

import com.istanbul.qurio.model.Answer
import com.istanbul.qurio.model.Quiz
import com.istanbul.qurio.repository.TriviaRepository
import com.istanbul.qurio.ui.base.BasePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayPresenter @Inject constructor(
    playView: PlayView,
    private val triviaRepository: TriviaRepository
) : BasePresenter<PlayView>() {
    private lateinit var quiz: Quiz
    private var currentQuestion: Int = 1
    private var currentScore: Int = 0
    private var questionStatus: QuestionStatus = QuestionStatus.WAITING

    init {
        attachView(playView)
    }

    fun onBackClick() {
        TODO("Not yet implemented")
    }

    fun getCoins() {
        val coins = 5 // TODO: get coins from repository
        view?.updateCoinsNumber(coins)
    }

    fun getQuiz(
        category: Int,
        difficulty: String
    ) {
        coroutineScope.launch {
            try {
                quiz = triviaRepository.getQuiz(
                    category = category,
                    difficulty = difficulty

                )
                showCurrentQuestion()
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun showCurrentQuestion() {
        if (currentQuestion <= MAX_NUM_OF_QUESTIONS) {
            view?.showQuestion(quiz.questions[currentQuestion], currentQuestion)
            questionStatus = QuestionStatus.WAITING
        }
    }

    fun startTimer() {
        TODO("Not yet implemented")
    }

    fun onTimerFinished() {
        TODO("Not yet implemented")
    }

    fun onAnswerClick(answer: Answer) {
        when (questionStatus) {
            is QuestionStatus.CHECKED -> {}
            is QuestionStatus.SELECTED -> {
                if (answer.text == (questionStatus as QuestionStatus.SELECTED).answer.text) {
                    checkAnswer(answer)
                } else {
                    selectAnswer(answer)
                }
            }
            QuestionStatus.WAITING -> {
                selectAnswer(answer)
            }
        }
    }

    private fun checkAnswer(answer: Answer) {
        if (answer.isCorrect) {
            view?.markAnswerCorrect(answer)
            currentScore++
        } else {
            view?.markAnswerWrong(answer)
        }
        questionStatus = QuestionStatus.CHECKED(answer.isCorrect)
        startMovingToNextQuestion()
    }

    private fun startMovingToNextQuestion() {
        TODO("Not yet implemented")
    }

    private fun selectAnswer(answer: Answer) {
        questionStatus = QuestionStatus.SELECTED(answer)
        view?.markAnswerSelected(answer)
    }

    fun onCheckClick() {
        TODO("Not yet implemented")
    }

    fun onSkipClick() {
        TODO("Not yet implemented")
    }

    fun onNextClick() {
        TODO("Not yet implemented")
    }

    private fun handleException(e: Exception) {
        TODO("Not yet implemented")
    }

    sealed interface QuestionStatus {
        data object WAITING: QuestionStatus
        data class SELECTED(val answer: Answer): QuestionStatus
        data class CHECKED(val win: Boolean): QuestionStatus
    }

    companion object {
        const val MAX_NUM_OF_QUESTIONS = 12
    }
}