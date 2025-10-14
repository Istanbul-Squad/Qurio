package com.istanbul.qurio.ui.play

import android.util.Log
import com.istanbul.qurio.model.Answer
import com.istanbul.qurio.model.Quiz
import com.istanbul.qurio.repository.TriviaRepository
import com.istanbul.qurio.ui.base.BasePresenter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayPresenter @Inject constructor(
    playView: PlayView,
    private val triviaRepository: TriviaRepository
) : BasePresenter<PlayView>() {
    private lateinit var quiz: Quiz
    private var currentQuestion: Int = 1
    private var currentScore: Int = 0
    private var questionStatus: QuestionStatus = QuestionStatus.WAITING

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

    fun getQuiz(
        category: Int,
        difficulty: String
    ) {
        coroutineScope.launch {
            try {
                view?.showLoading()
                quiz = triviaRepository.getQuiz(
                    category = category,
                    difficulty = difficulty
                )
                val newAnswers = quiz.questions.shuffled()
                quiz = Quiz(newAnswers)
                showCurrentQuestion()
                view?.hideLoading()
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun showCurrentQuestion() {
        if (currentQuestion <= MAX_NUM_OF_QUESTIONS) {
            view?.showQuestion(quiz.questions[currentQuestion - 1], currentQuestion)
            questionStatus = QuestionStatus.WAITING
        }
    }

    fun startTimer() {
        TODO("Not yet implemented")
    }

    fun onTimerFinished() {
        TODO("Not yet implemented")
    }

    fun onAnswerClick(answer: Answer) {
        if (questionStatus == QuestionStatus.WAITING || questionStatus is QuestionStatus.SELECTED) {
            if (answer.choiceStatus == Answer.ChoiceStatus.NotSelected) {
                selectAnswer(answer)
            }
        }
    }

    private fun selectAnswer(answer: Answer) {
        questionStatus = QuestionStatus.SELECTED(answer)
        view?.markAnswerSelected(answer.copy(choiceStatus = Answer.ChoiceStatus.Selected))
    }

    fun onCheckClick() {
        if (questionStatus is QuestionStatus.SELECTED) {
            val answer = (questionStatus as QuestionStatus.SELECTED).answer
            checkAnswer(answer)
        }
    }

    private fun checkAnswer(answer: Answer) {
        if (answer.isCorrect) {
            view?.markAnswerCorrect(answer.copy(choiceStatus = Answer.ChoiceStatus.Chosen))
            currentScore++
        } else {
            view?.markAnswerWrong(answer.copy(choiceStatus = Answer.ChoiceStatus.Chosen))
        }
        questionStatus = QuestionStatus.CHECKED(answer.isCorrect)
        convertToNextView()
    }

    private fun convertToNextView() {
        view?.convertToNextButton()
    }

    fun onSkipClick() {
        navigateToNext()
    }

    fun onNextClick() {
        navigateToNext()
    }

    private fun handleException(e: Exception) {
//        TODO("Not yet implemented")
        Log.e("PlayPresenter", "handleException: $e")
    }

    private fun navigateToNext() {
        if (currentQuestion < MAX_NUM_OF_QUESTIONS) {
            currentQuestion++
            showCurrentQuestion()
            reset()
        } else {
            goToResult()
        }
    }

    private fun reset() {
        view?.convertToCheckButton()
        questionStatus = QuestionStatus.WAITING
    }

    private fun goToResult() {
//        TODO("Not yet implemented")
    }

    sealed interface QuestionStatus {
        data object WAITING : QuestionStatus
        data class SELECTED(val answer: Answer) : QuestionStatus
        data class CHECKED(val win: Boolean) : QuestionStatus
    }

    companion object {
        const val MAX_NUM_OF_QUESTIONS = 12
    }
}