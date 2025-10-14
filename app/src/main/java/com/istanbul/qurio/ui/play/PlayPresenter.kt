package com.istanbul.qurio.ui.play

import com.istanbul.qurio.model.Answer
import com.istanbul.qurio.model.Quiz
import com.istanbul.qurio.repository.TriviaRepository
import com.istanbul.qurio.ui.base.BasePresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayPresenter @Inject constructor(
    playView: PlayView,
    private val triviaRepository: TriviaRepository
) : BasePresenter<PlayView>() {
    private lateinit var quiz: Quiz
    private var currentQuestion: Int = 1

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
                view?.showLoading()
                quiz = triviaRepository.getQuiz(
                    category = category,
                    difficulty = difficulty

                )
                showCurrentQuestion()
                view?.hideLoading()
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun showCurrentQuestion() {
        if (currentQuestion <= MAX_NUM_OF_QUESTIONS) {
            view?.showQuestion(quiz.questions[currentQuestion], currentQuestion)
        }
    }

    fun startTimer() {
        TODO("Not yet implemented")
    }

    fun onTimerFinished() {
        TODO("Not yet implemented")
    }

    fun onAnswerClick(answer: Answer) {
        TODO("Not yet implemented")
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

    companion object {
        const val MAX_NUM_OF_QUESTIONS = 12
    }
}