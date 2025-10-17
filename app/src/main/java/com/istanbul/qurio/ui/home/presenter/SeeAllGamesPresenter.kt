package com.istanbul.qurio.ui.home.presenter

import com.istanbul.qurio.repository.TriviaRepository
import com.istanbul.qurio.ui.base.BasePresenter
import com.istanbul.qurio.ui.home.SeeAllGamesView
import com.istanbul.qurio.ui.home.toUIModel
import javax.inject.Inject

class SeeAllGamesPresenter @Inject constructor(
    private val triviaRepository: TriviaRepository,
    view: SeeAllGamesView
) : BasePresenter<SeeAllGamesView>() {

    init {
        attachView(view)
    }

    fun loadAllGames() {
        tryToExecute(
            execute = {
                triviaRepository.getGames().map { it.toUIModel() }
            },
            onSuccess = { games ->
                view?.showAllGames(games)
            },
            onError = {
                view?.showError(it.message ?: "Failed to load games")
            }
        )
    }
}