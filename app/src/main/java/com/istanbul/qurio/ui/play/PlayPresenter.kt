package com.istanbul.qurio.ui.play

import com.istanbul.qurio.model.Answer
import com.istanbul.qurio.ui.base.BasePresenter
import javax.inject.Inject

class PlayPresenter @Inject constructor(
    playView: PlayView
) : BasePresenter<PlayView>() {
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

    fun getQuiz() {
        TODO("Not yet implemented")
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
}