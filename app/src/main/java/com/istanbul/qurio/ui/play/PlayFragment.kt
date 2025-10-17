package com.istanbul.qurio.ui.play

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.istanbul.qurio.QurioApplication
import com.istanbul.qurio.R
import com.istanbul.qurio.databinding.FragmentPlayBinding
import com.istanbul.qurio.model.Answer
import com.istanbul.qurio.model.Question
import com.istanbul.qurio.repository.TriviaRepository
import com.istanbul.qurio.ui.base.BaseFragment
import com.istanbul.qurio.ui.play.PlayPresenter.Companion.MAX_NUM_OF_QUESTIONS
import com.istanbul.qurio.ui.play.adapter.AnswerAdapter
import com.istanbul.qurio.ui.play.adapter.OnAnswerClickListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayFragment : BaseFragment<FragmentPlayBinding>(FragmentPlayBinding::inflate), PlayView,
    OnAnswerClickListener {
    private lateinit var playPresenter: PlayPresenter

    private var answerAdapter: AnswerAdapter = AnswerAdapter(listOf(), this)

    @Inject
    lateinit var triviaRepository: TriviaRepository

    private var timerAnimator: ObjectAnimator? = null


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
        playPresenter = PlayPresenter(this, triviaRepository)
        binding.recyclerAnswerOptions.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerAnswerOptions.adapter = answerAdapter
        binding.recyclerAnswerOptions.addItemDecoration(VerticalSpaceItemDecoration(12))

        playPresenter.getCoins()
        val args = PlayFragmentArgs.fromBundle(requireArguments())
        val categoryId = args.categoryId
        val difficulty = args.difficulty

        playPresenter.getQuiz(
            category = categoryId,
            difficulty = difficulty
        )

        initClickListeners()
    }

    override fun updateCoinsNumber(number: Int) {
        binding.textNumberOfCoins.text = number.toString()
    }

    override fun updateLivesNumber(number: Int) {
        binding.textNumberOfLives.text = number.toString()
    }

    override fun showQuestion(question: Question, index: Int) {
        val decodedText = HtmlCompat.fromHtml(question.question, HtmlCompat.FROM_HTML_MODE_LEGACY)

        binding.cardQuestion.numberOfQuestion.text =
            getString(R.string.question_formatted, index, MAX_NUM_OF_QUESTIONS)
        binding.cardQuestion.questionText.text = decodedText
        answerAdapter.setData(question.answers)

        startTime()
    }

    override fun markAnswerCorrect(answer: Answer) {
        Log.d("PlayFragment", "markAnswerCorrect: $answer")
        answerAdapter.setData(
            answerAdapter.getList().toMutableList().map {
                if (it.text == answer.text) answer else it.copy(
                    choiceStatus = Answer.ChoiceStatus.NotSelected,
                    isCorrect = true
                )
            })
    }

    override fun markAnswerWrong(answer: Answer) {
        Log.d("PlayFragment", "markAnswerWrong: $answer")
        answerAdapter.setData(
            answerAdapter.getList().toMutableList().map {
                if (it.text == answer.text) answer else it.copy(
                    choiceStatus = Answer.ChoiceStatus.NotSelected,
                    isCorrect = false
                )
            })
    }

    override fun markAnswerSelected(answer: Answer) {
        answerAdapter.setData(
            answerAdapter.getList().toMutableList()
                .map { if (it.text == answer.text) answer else it.copy(choiceStatus = Answer.ChoiceStatus.NotSelected) })
    }

    override fun convertToNextButton() {
        binding.buttonCheck.visibility = View.GONE
        binding.buttonSkip.visibility = View.GONE
        binding.buttonNext.visibility = View.VISIBLE
    }

    override fun convertToCheckButton() {
        binding.buttonCheck.visibility = View.VISIBLE
        binding.buttonSkip.visibility = View.VISIBLE
        binding.buttonNext.visibility = View.GONE
    }

    override fun markAnswerSkipped(answer: Answer) {
        answerAdapter.setData(
            answerAdapter.getList().toMutableList()
                .map { if (it.text == answer.text) answer else it.copy(choiceStatus = Answer.ChoiceStatus.NotSelected) })
    }

    private fun initClickListeners() {
        binding.buttonCheck.setOnClickListener {
            playPresenter.onCheckClick()
        }

        binding.buttonSkip.setOnClickListener {
            playPresenter.onSkipClick()
        }

        binding.buttonNext.setOnClickListener {
            playPresenter.onNextClick()
        }

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun startTime() {
        val progressBar = binding.progressBar.timerBar
        val secondsText = binding.progressBar.numberOfSeconds

        timerAnimator?.cancel()
        progressBar.progress = 100

        timerAnimator = ObjectAnimator.ofInt(progressBar, "progress", 100, 0).apply {
            duration = 10_000L
            interpolator = LinearInterpolator()

            addUpdateListener { animation ->
                val remainingPercent = animation.animatedValue as Int
                val remainingSeconds = (remainingPercent * 10) / 100
                secondsText.text = getString(R.string.seconds_left, remainingSeconds)
            }

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    playPresenter.onTimeFinished()
                }
            })
        }

        timerAnimator?.start()
    }

    override fun stopTimer() {
        timerAnimator?.removeAllListeners()
        timerAnimator?.cancel()
    }

    override fun startTimer() {
        startTime()
    }

    override fun goToResult(
        correctAnswersCount: Int,
        inCorrectAnswersCount: Int,
        skippedAnswersCount: Int
    ) {
        val action = PlayFragmentDirections.actionPlayFragmentToResultFragment(
            correctAnswersCount = correctAnswersCount,
            inCorrectAnswersCount = inCorrectAnswersCount,
            skippedAnswersCount = skippedAnswersCount
        )
        findNavController().navigate(action)
    }

    override fun onAnswerClick(answer: Answer) {
        playPresenter.onAnswerClick(answer)
    }

     override fun showBuyLifeDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_buy, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        val btnBuy = dialogView.findViewById<Button>(R.id.btn_buy)
        val btnCancel = dialogView.findViewById<Button>(R.id.btn_cancel)
        val close = dialogView.findViewById<ImageView>(R.id.close_shape)

        btnBuy.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val player = triviaRepository.getPlayerOrCreate()
                if (player.coins >= 200) {
                    triviaRepository.buyLife()
                    Toast.makeText(requireContext(), "Life purchased!", Toast.LENGTH_SHORT).show()
                    playPresenter.initPlayer()
                } else {
                    Toast.makeText(requireContext(), "Not enough coins!", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.dismiss()
        }

        btnCancel.setOnClickListener { dialog.dismiss() }
        close.setOnClickListener { dialog.dismiss() }
    }

    override fun onDestroyView() {
        playPresenter.coroutineScope.cancel()
        timerAnimator?.cancel()
        timerAnimator = null
        super.onDestroyView()
    }
}