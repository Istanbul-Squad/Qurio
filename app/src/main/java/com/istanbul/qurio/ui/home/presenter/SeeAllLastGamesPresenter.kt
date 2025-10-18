package com.istanbul.qurio.ui.home.presenter

import com.istanbul.qurio.database.QuizDao
import com.istanbul.qurio.ui.base.BasePresenter
import com.istanbul.qurio.ui.home.SeeAllLastGamesView
import javax.inject.Inject

class SeeAllLastGamesPresenter @Inject constructor(
    private val quizDao: QuizDao,
    view: SeeAllLastGamesView
) : BasePresenter<SeeAllLastGamesView>() {

    init {
        attachView(view)
    }

    fun loadAllLastGames() {
        tryToExecute(
            execute = { quizDao.getAllResults() },
            onSuccess = { results ->
                view?.showLastGames(results)
            },
            onError = {
                view?.showError(it.message ?: "Failed to load last games")
            }
        )
    }
}