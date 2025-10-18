package com.istanbul.qurio.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.istanbul.qurio.QurioApplication
import com.istanbul.qurio.database.QuizDao
import com.istanbul.qurio.databinding.FragmentSeeAllLastgameBinding
import com.istanbul.qurio.model.QuizResult
import com.istanbul.qurio.ui.base.BaseFragment
import com.istanbul.qurio.ui.home.adapter.LastGamesAdapter
import com.istanbul.qurio.ui.home.presenter.SeeAllLastGamesPresenter
import javax.inject.Inject

class SeeAllLastGamesFragment :
    BaseFragment<FragmentSeeAllLastgameBinding>(FragmentSeeAllLastgameBinding::inflate),
    SeeAllLastGamesView {

    @Inject
    lateinit var quizDao: QuizDao

    private lateinit var presenter: SeeAllLastGamesPresenter
    private lateinit var adapter: LastGamesAdapter

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
        setupRecycler()
        setupPresenter()
        setupListeners()
        presenter.loadAllLastGames()
    }

    private fun setupRecycler() = with(binding) {
        adapter = LastGamesAdapter()
        recyclerViewLeft.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewLeft.adapter = adapter
    }

    private fun setupPresenter() {
        presenter = SeeAllLastGamesPresenter(quizDao, this)
    }

    private fun setupListeners() = with(binding) {
        lastGame.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun showLastGames(games: List<QuizResult>) {
        adapter.submitList(games)
    }

    override fun showError(message: String) {
       // android.widget.Toast.makeText(requireContext(), message, android.widget.Toast.LENGTH_SHORT).show()
    }
}
