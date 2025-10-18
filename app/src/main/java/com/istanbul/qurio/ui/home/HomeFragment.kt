package com.istanbul.qurio.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.istanbul.qurio.QurioApplication
import com.istanbul.qurio.R
import com.istanbul.qurio.databinding.FragmentHomeBinding
import com.istanbul.qurio.model.QuizResult
import com.istanbul.qurio.model.UserEntity
import com.istanbul.qurio.model.UserStatisticsEntity
import com.istanbul.qurio.repository.TriviaRepository
import com.istanbul.qurio.ui.base.BaseFragment
import com.istanbul.qurio.ui.home.adapter.GameAdapter
import com.istanbul.qurio.ui.home.adapter.LastGamesAdapter
import com.istanbul.qurio.ui.home.dialogs.DifficultyDialog
import com.istanbul.qurio.ui.home.dialogs.SettingsDialog
import com.istanbul.qurio.ui.home.presenter.HomePresenter
import java.text.DateFormatSymbols
import java.util.Locale
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), HomeView {

    @Inject
    lateinit var triviaRepository: TriviaRepository

    private lateinit var presenter: HomePresenter
    private lateinit var gameAdapter: GameAdapter
    private lateinit var lastGamesAdapter: LastGamesAdapter
    private lateinit var streakAdapter: StreakDayAdapter

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
            characterName.text = user.name.ifBlank { getString(R.string.rika) }

            Glide.with(requireContext())
                .load(user.profileImageUrl.ifBlank { null })
                .placeholder(R.drawable.im_food)
                .error(R.drawable.im_food)
                .into(characterImage)
        }
    }

    override fun showUserStatistics(stats: UserStatisticsEntity) {
        updateStatisticsComponent(stats)
        updateStreakComponent(stats)
    }

    private fun updateStatisticsComponent(stats: UserStatisticsEntity) = with(binding.statisticsComponent) {
        livesCount.text = stats.lives.toString()
        pointsCount.text = stats.points.toString()
        trophiesCount.text = stats.trophies.toString()
    }

    private fun updateStreakComponent(stats: UserStatisticsEntity) = with(binding.streakComponent) {
        numberOfDay.text = getString(R.string.streak_days_format, stats.streakDays)
        description.text = getString(if (stats.streakDays > 0) R.string.keep_it_up else R.string.start_your_streak)

        val weekdays = DateFormatSymbols(Locale.getDefault()).shortWeekdays
        val orderedDays = listOf(weekdays[7], weekdays[1], weekdays[2], weekdays[3], weekdays[4], weekdays[5], weekdays[6])
        streakAdapter.submitList(
            orderedDays.mapIndexed { index, day ->
                StreakDayUiState(day, index < stats.streakDays.coerceIn(0, orderedDays.size))
            }
        )
    }

    private fun setupRecyclerViews() = with(binding) {
        gameAdapter = GameAdapter { game ->
            showDifficultyDialog(game)
        }
        categoryGameHorizontal.apply {
            adapter = gameAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        lastGamesAdapter = LastGamesAdapter()
        lastGameVertical.apply {
            adapter = lastGamesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        streakAdapter = StreakDayAdapter(object : StreakDayInteractionListener {
            override fun onClickDay(position: Int) {
            }
        })
        streakComponent.recyclerView.apply {
            adapter = streakAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupPresenter() {
        presenter = HomePresenter(triviaRepository, this)
    }

    private fun setupListeners() = with(binding) {
        headerGamesSeeAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_seeAllGamesFragment)
        }

        allLastGame.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_seeAllLastGamesFragment)
        }

        tobbBar.settingIcon.setOnClickListener {
            showSettingsDialog()
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

    private fun showSettingsDialog() {
        val dialog = SettingsDialog(
            context = requireContext(),
            onSave = { musicVolume, soundVolume ->
                saveSettings(musicVolume, soundVolume)
            },
            onDiscard = {
                resetSettingsToDefault()
            }
        )
        dialog.show()
    }

    private fun saveSettings(musicVolume: Int, soundVolume: Int) {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putInt(KEY_MUSIC_VOLUME, musicVolume)
            .putInt(KEY_SOUND_VOLUME, soundVolume)
            .apply()
    }

    private fun resetSettingsToDefault() {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putInt(KEY_MUSIC_VOLUME, DEFAULT_MUSIC_VOLUME)
            .putInt(KEY_SOUND_VOLUME, DEFAULT_SOUND_VOLUME)
            .apply()
    }

    companion object {
        private const val PREFS_NAME = "settings"
        private const val KEY_MUSIC_VOLUME = "music_volume"
        private const val KEY_SOUND_VOLUME = "sound_volume"

        const val DEFAULT_MUSIC_VOLUME = 50
        const val DEFAULT_SOUND_VOLUME = 50
    }
}