package com.istanbul.qurio.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.istanbul.qurio.QurioApplication
import com.istanbul.qurio.databinding.FragmentSeeAllGamesBinding
import com.istanbul.qurio.repository.TriviaRepository
import com.istanbul.qurio.ui.base.BaseFragment
import com.istanbul.qurio.ui.home.adapter.GameAdapter
import com.istanbul.qurio.ui.home.dialogs.DifficultyDialog
import com.istanbul.qurio.ui.home.presenter.SeeAllGamesPresenter

import javax.inject.Inject

class SeeAllGamesFragment :
    BaseFragment<FragmentSeeAllGamesBinding>(FragmentSeeAllGamesBinding::inflate),
    SeeAllGamesView {

    @Inject
    lateinit var triviaRepository: TriviaRepository

    private lateinit var presenter: SeeAllGamesPresenter
    private lateinit var adapter: GameAdapter

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
        presenter.loadAllGames()
    }

    private fun setupRecycler() = with(binding) {
        adapter = GameAdapter { game ->
            showDifficultyDialog(game)
        }
        recyclerViewGames.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerViewGames.adapter = adapter
    }

    private fun setupPresenter() {
        presenter = SeeAllGamesPresenter(triviaRepository, this)
    }

    private fun setupListeners() = with(binding) {
        games.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun showAllGames(games: List<GameCategoryUIModel>) {
        adapter.submitList(games)
    }

    override fun showError(message: String) {
        //android.widget.Toast.makeText(requireContext(), message, android.widget.Toast.LENGTH_SHORT).show()
    }

    private fun showDifficultyDialog(game: GameCategoryUIModel) {
        val dialog = DifficultyDialog(requireContext()) { selectedDifficulty ->
            val action = HomeFragmentDirections.actionHomeFragmentToPlayFragment(
                categoryId = game.id,
                difficulty = selectedDifficulty
            )
            findNavController().navigate(action)
        }
        dialog.show()
    }
}