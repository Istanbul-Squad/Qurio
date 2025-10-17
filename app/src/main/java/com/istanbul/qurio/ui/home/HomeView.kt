package com.istanbul.qurio.ui.home

import com.istanbul.qurio.model.QuizResult
import com.istanbul.qurio.model.UserEntity
import com.istanbul.qurio.model.UserStatisticsEntity
import com.istanbul.qurio.ui.base.BaseView

interface HomeView: BaseView{
    fun showGames(games: List<GameCategoryUIModel>)
    fun showLastResults(results: List<QuizResult>)
    fun showError(message: String)
    fun showUserStatistics(stats: UserStatisticsEntity)
    fun showUserInfo(user: UserEntity)
}
