package com.istanbul.qurio.ui.home


import com.istanbul.qurio.model.QuizResult
import com.istanbul.qurio.ui.base.BaseView

interface SeeAllLastGamesView : BaseView {
    fun showLastGames(games: List<QuizResult>)
    fun showError(message: String)
}
