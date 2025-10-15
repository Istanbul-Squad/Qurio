package com.istanbul.qurio.ui.result

import android.util.Log
import com.istanbul.qurio.model.QuizResult
import com.istanbul.qurio.repository.TriviaRepository
import com.istanbul.qurio.ui.base.BasePresenter
import javax.inject.Inject

class ResultPresenter @Inject constructor(
    view: ResultView,
    private val triviaRepository: TriviaRepository
) : BasePresenter<ResultView>() {

    init {
        attachView(view)
    }

    fun insertQuizResult(quizResult: QuizResult) {
        tryToExecute(
            execute = { triviaRepository.insertQuizResult(quizResult) },
            onError = {
                Log.e(TAG, "insertQuizResult: ${it.message}")
            }
        )
    }

    companion object {
        private const val TAG = "ResultPresenter"
    }
}