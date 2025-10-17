package com.istanbul.qurio.ui.play

import android.util.Log
import com.istanbul.qurio.model.Answer
import com.istanbul.qurio.model.Player
import com.istanbul.qurio.model.Quiz
import com.istanbul.qurio.repository.TriviaRepository
import com.istanbul.qurio.ui.base.BasePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayPresenter @Inject constructor(
    playView: PlayView,
    private val triviaRepository: TriviaRepository
) : BasePresenter<PlayView>() {

    private var player: Player? = null
    private lateinit var quiz: Quiz
    private var currentQuestion: Int = 1
    private var currentScore: Int = 0
    private var questionStatus: QuestionStatus = QuestionStatus.WAITING
    private var skippedAnswersCount: Int = 0

    init {
        attachView(playView)
        initPlayer()
    }

    fun initPlayer() {
        coroutineScope.launch {
            player = triviaRepository.getPlayerOrCreate()
            player?.let {
                view?.updateCoinsNumber(it.lives)
            }
        }
    }

    fun getQuiz(category: Int, difficulty: String) {
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
            } catch (e: Exception) {
                handleException(e)
            } finally {
                view?.hideLoading()
            }
        }
    }

    fun showCurrentQuestion() {
        if (currentQuestion <= MAX_NUM_OF_QUESTIONS) {
            view?.showQuestion(quiz.questions[currentQuestion - 1], currentQuestion)
            questionStatus = QuestionStatus.WAITING
        }
    }

    fun onTimeFinished() {
        if (questionStatus !is QuestionStatus.CHECKED) {
            val question = quiz.questions[currentQuestion - 1]

            val wrongAnswer = Answer(
                text = question.question,
                isCorrect = false,
                skipped = false
            )
            view?.markAnswerWrong(wrongAnswer)
            questionStatus = QuestionStatus.CHECKED(false)

            coroutineScope.launch {
                kotlinx.coroutines.delay(500)
                navigateToNext()
            }
        }
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
        view?.stopTimer()

        when {
            answer.isCorrect -> {
                currentScore++
                view?.markAnswerCorrect(answer.copy(choiceStatus = Answer.ChoiceStatus.Chosen))
            }

            else -> {
                view?.markAnswerWrong(answer.copy(choiceStatus = Answer.ChoiceStatus.Chosen))
                coroutineScope.launch {
                    triviaRepository.loseLife()
                    val lives = updateLivesView()
                    if (lives <= 0) {
                        view?.showBuyLifeDialog()
                    }
                }
            }
        }

        questionStatus = QuestionStatus.CHECKED(answer.isCorrect)
        convertToNextView()
    }

    private fun convertToNextView() {
        view?.convertToNextButton()
    }

    private suspend fun updateLivesView(): Int {
        val lives = triviaRepository.getNumberOfLife()
        view?.updateCoinsNumber(lives)
        return lives
    }

    fun onSkipClick() {
        if (questionStatus !is QuestionStatus.CHECKED) {

            view?.stopTimer()
            skippedAnswersCount++

            val question = quiz.questions[currentQuestion - 1]
            val skippedAnswer = Answer(
                text = question.question,
                isCorrect = false,
                skipped = true
            )

            view?.markAnswerSkipped(skippedAnswer)
            questionStatus = QuestionStatus.CHECKED(false)
            navigateToNext()
        }
    }

    fun onNextClick() {
        navigateToNext()
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
        val incorrectCount =
            MAX_NUM_OF_QUESTIONS - currentScore - skippedAnswersCount

        view?.goToResult(
            correctAnswersCount = currentScore,
            inCorrectAnswersCount = incorrectCount,
            skippedAnswersCount = skippedAnswersCount
        )
    }

    private fun handleException(e: Exception) {
        Log.e("PlayPresenter", "handleException: ${e.message}", e)
        view?.hideLoading()
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