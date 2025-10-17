package com.istanbul.qurio.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.istanbul.qurio.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.istanbul.qurio.QurioApplication
import com.istanbul.qurio.database.QuizDao
import com.istanbul.qurio.databinding.FragmentHomeBinding
import com.istanbul.qurio.model.QuizResult
import com.istanbul.qurio.model.UserEntity
import com.istanbul.qurio.model.UserStatisticsEntity
import com.istanbul.qurio.repository.TriviaRepository
import com.istanbul.qurio.ui.base.BaseFragment
import com.istanbul.qurio.ui.home.adapter.GameAdapter
import com.istanbul.qurio.ui.home.adapter.LastGamesAdapter
import com.istanbul.qurio.ui.home.dialogs.DifficultyDialog
import com.istanbul.qurio.ui.home.presenter.HomePresenter
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), HomeView {

    @Inject
    lateinit var triviaRepository: TriviaRepository

    @Inject
    lateinit var quizDao: QuizDao

    private lateinit var presenter: HomePresenter
    private lateinit var gameAdapter: GameAdapter
    private lateinit var lastGamesAdapter: LastGamesAdapter

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

        setupRecyclerViews()
        setupPresenter()
        setupListeners()
        presenter.loadUserStatistics()
        presenter.loadHomeData()
        presenter.loadUserInfo()
    }

    override fun showUserInfo(user: UserEntity) {
        with(binding.tobbBar) {
            val displayName = if (user.name.isNullOrBlank()) {
                getString(R.string.rika)
            } else {
                user.name
            }

            val imageUrl = if (user.profileImageUrl.isNullOrBlank()) {
                null
            } else {
                user.profileImageUrl
            }

            characterName.text = displayName

            Glide.with(requireContext())
                .load(imageUrl)
                .placeholder(R.drawable.im_food)
                .error(R.drawable.im_food)
                .into(characterImage)
        }
    }

    override fun showUserStatistics(stats: UserStatisticsEntity) {
        with(binding.statisticsComponent) {
            livesCount.text = stats.lives.toString()
            pointsCount.text = stats.points.toString()
            trophiesCount.text = stats.trophies.toString()
        }

        with(binding.streakComponent) {
            numberOfDay.text = "${stats.streakDays} day streak"
            description.text = if (stats.streakDays > 0) "KEEP IT UP!" else "Start your streak!"

            val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
            val streakList = days.mapIndexed { index, day ->
                StreakDayUiState(day = day, isSelected = index < stats.streakDays)
            }.toMutableList()

            val adapter = StreakDayAdapter(streakList, object : StreakDayInteractionListener {
                override fun onClickDay(position: Int) {}
            })

            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapter
        }
    }

    private fun setupRecyclerViews() = with(binding) {
        gameAdapter = GameAdapter { game ->
            showDifficultyDialog(game)
        }
        categoryGameHorizontal.apply {
            adapter = gameAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        lastGamesAdapter = LastGamesAdapter()
        lastGameVertical.apply {
            adapter = lastGamesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupPresenter() {
        presenter = HomePresenter(triviaRepository, quizDao, this)
    }

    private fun setupListeners() = with(binding) {
        headerGamesSeeAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_seeAllGamesFragment)
        }

        allLastGame.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_seeAllLastGamesFragment)
        }
    }

    override fun showGames(games: List<GameCategoryUIModel>) {
        gameAdapter.submitList(games)
    }

    override fun showLastResults(results: List<QuizResult>) {
        lastGamesAdapter.submitList(results)
    }

    override fun showError(message: String) {
       // lifecycleScope.launch(Dispatchers.Main) {
          //  Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
       // }
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