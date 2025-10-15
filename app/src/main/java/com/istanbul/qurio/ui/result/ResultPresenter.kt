package com.istanbul.qurio.ui.result

import android.util.Log
import com.istanbul.qurio.model.QuizResult
import com.istanbul.qurio.model.ResultSummary
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

    fun calculateResultSummary(
        correct: Int,
        incorrect: Int,
        skipped: Int
    ): ResultSummary {
        val totalQuestions = correct + incorrect + skipped

        var score = correct * 10 - incorrect * 2
        if (score < 0) score = 0

        val correctPercentage = if (totalQuestions > 0) {
            (correct * 100) / totalQuestions
        } else 0

        val stars = when {
            correctPercentage == 100 -> 3
            correctPercentage >= 80 -> 2
            correctPercentage >= 50 -> 1
            else -> 0
        }

        val reward = (score / 2) + (stars * 5)

        return ResultSummary(
            score = score,
            stars = stars,
            reward = reward
        )
    }

    companion object {
        private const val TAG = "ResultPresenter"
    }
}