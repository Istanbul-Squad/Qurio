package com.istanbul.qurio.ui.home

import com.istanbul.qurio.ui.base.BaseView

interface SeeAllGamesView : BaseView {
    fun showAllGames(games: List<GameCategoryUIModel>)
    fun showError(message: String)
}
