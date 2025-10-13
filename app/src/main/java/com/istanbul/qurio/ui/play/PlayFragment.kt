package com.istanbul.qurio.ui.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.istanbul.qurio.QurioApplication
import com.istanbul.qurio.R
import com.istanbul.qurio.databinding.FragmentPlayBinding
import com.istanbul.qurio.model.Question
import com.istanbul.qurio.repository.TriviaRepository
import com.istanbul.qurio.ui.base.BaseFragment
import com.istanbul.qurio.ui.play.PlayPresenter.Companion.MAX_NUM_OF_QUESTIONS
import com.istanbul.qurio.ui.play.adapter.AnswerAdapter
import javax.inject.Inject

class PlayFragment: BaseFragment<FragmentPlayBinding>(FragmentPlayBinding::inflate), PlayView {
    private lateinit var playPresenter: PlayPresenter
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
        playPresenter = PlayPresenter(this, triviaRepository)
        playPresenter.getCoins()
        playPresenter.getQuiz(
            category = 10, // Entertainment: Books
            difficulty = "easy"
        ) // TODO: get category and difficulty from navigation arguments
    }

    override fun updateCoinsNumber(number: Int) {
        binding.textNumberOfCoins.text = number.toString()
    }

    override fun showQuestion(question: Question, index: Int) {
        val decodedText = HtmlCompat.fromHtml(question.question, HtmlCompat.FROM_HTML_MODE_LEGACY)

        binding.cardQuestion.numberOfQuestion.text =
            getString(R.string.question_formatted, index, MAX_NUM_OF_QUESTIONS)
        binding.cardQuestion.questionText.text = decodedText
        binding.recyclerAnswerOptions.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerAnswerOptions.adapter = AnswerAdapter(question.answers)
        binding.recyclerAnswerOptions.addItemDecoration(VerticalSpaceItemDecoration(12))

    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }
}