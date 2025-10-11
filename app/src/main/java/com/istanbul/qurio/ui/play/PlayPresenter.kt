package com.istanbul.qurio.ui.play

import com.istanbul.qurio.ui.base.BasePresenter
import javax.inject.Inject

class PlayPresenter @Inject constructor(
    playView: PlayView
) : BasePresenter<PlayView>() {
    init {
        attachView(playView)
    }

    fun getCoins() {
        val coins = 5
        view?.updateCoinsNumber(coins)
    }
}