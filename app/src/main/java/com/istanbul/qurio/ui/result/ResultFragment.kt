package com.istanbul.qurio.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.istanbul.qurio.QurioApplication
import com.istanbul.qurio.R
import com.istanbul.qurio.databinding.FragmentResultBinding
import com.istanbul.qurio.model.QuizResult
import com.istanbul.qurio.model.ResultSummary
import com.istanbul.qurio.repository.TriviaRepository
import com.istanbul.qurio.ui.base.BaseFragment
import javax.inject.Inject

class ResultFragment : BaseFragment<FragmentResultBinding>(FragmentResultBinding::inflate),
    ResultView {

    private val args: ResultFragmentArgs by navArgs()
    private lateinit var resultPresenter: ResultPresenter

    @Inject
    lateinit var triviaRepository: TriviaRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().application as QurioApplication).appComponent.inject(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resultPresenter = ResultPresenter(
            view = this,
            triviaRepository = triviaRepository
        )

        binding.result.textScoreCorrectValue.text = args.correctAnswersCount.toString()
        binding.result.textScoreIncorrectValue.text = args.inCorrectAnswersCount.toString()
        binding.result.textScoreSkippedValue.text = args.skippedAnswersCount.toString()

        val summary = calculateResultSummary(
            correct = args.correctAnswersCount,
            incorrect = args.inCorrectAnswersCount,
            skipped = args.skippedAnswersCount
        )

        displayResultSummary(summary)

        resultPresenter.insertQuizResult(
            QuizResult(
                correctAnswers = args.correctAnswersCount,
                incorrectAnswers = args.inCorrectAnswersCount,
                skippedAnswers = args.skippedAnswersCount,
                coinsEarned = summary.reward,
            )
        )
    }

    private fun calculateResultSummary(
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

    private fun displayResultSummary(summary: ResultSummary) {
        binding.result.textNumberOfCoins.text = summary.reward.toString()

        val imageRes = when (summary.stars) {
            3 -> R.drawable.im_great_job_3_star
            2 -> R.drawable.im_great_job_2_star
            1 -> R.drawable.im_great_job_1_star
            else -> R.drawable.im_you_lose
        }

        binding.result.imageGreatJob.setImageResource(imageRes)
    }
}
