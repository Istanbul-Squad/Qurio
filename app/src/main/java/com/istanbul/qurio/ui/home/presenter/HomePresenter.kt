package com.istanbul.qurio.ui.home.presenter

import com.istanbul.qurio.database.QuizDao
import com.istanbul.qurio.repository.TriviaRepository
import com.istanbul.qurio.ui.base.BasePresenter
import com.istanbul.qurio.ui.home.HomeView
import com.istanbul.qurio.ui.home.toUIModel
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val triviaRepository: TriviaRepository,
    private val quizDao: QuizDao,
    view: HomeView
) : BasePresenter<HomeView>() {

    init {
        attachView(view)
    }

    fun loadHomeData() {
        tryToExecute(
            execute = {
                val games = triviaRepository.getGames()
                val results = quizDao.getAllResults().take(10)
                Pair(games, results)
            },
            onSuccess = { (games, results) ->
                view?.apply {
                    showGames(games.map { it.toUIModel() })
                    showLastResults(results)
                }
            },
            onError = {
                view?.showError(it.message ?: "Something went wrong")
            }
        )
    }

    fun loadUserStatistics() {
        tryToExecute(
            execute = {
                triviaRepository.getUserStatistics()
            },
            onSuccess = { stats ->
                view?.showUserStatistics(stats)
            },
            onError = {
                view?.showError(it.message ?: "Failed to load statistics")
            }
        )
    }

    fun loadUserInfo() {
        tryToExecute(
            execute = {
                triviaRepository.getUser()
            },
            onSuccess = { user ->
                view?.showUserInfo(user)
            },
            onError = {
                view?.showError(it.message ?: "Failed to load user info")
            }
        )
    }

}