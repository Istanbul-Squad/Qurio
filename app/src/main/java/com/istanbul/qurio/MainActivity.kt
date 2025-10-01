package com.istanbul.qurio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.istanbul.qurio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val streakStates = mutableListOf<StreakUiState>()
    private lateinit var adapter: StreakAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val days = listOf("S", "M", "T", "W", "Th", "F", "S")
        streakStates.addAll(days.map { StreakUiState(it) })

        adapter = StreakAdapter(streakStates) { position ->
            streakStates[position] = streakStates[position].copy(isActive = !streakStates[position].isActive)
            adapter.notifyItemChanged(position)
//            updateStreakText()
        }
        binding.recyclerView.adapter = adapter

//        updateStreakText()
    }

//    private fun updateStreakText() {
//        val count = streakStates.count { it.isActive }
//        val message = if (count > 0) "make a big series" else "start make a series"
//        binding.numberOfDay.text = "$count day streak, $message"
//    }
}