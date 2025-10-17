package com.istanbul.qurio.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.istanbul.qurio.R
import com.istanbul.qurio.databinding.ItemStreakBinding

class StreakDayAdapter(
    private val listener: StreakDayInteractionListener
) : ListAdapter<StreakDayUiState, StreakDayAdapter.StreakViewHolder>(StreakDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreakViewHolder {
        val binding = ItemStreakBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StreakViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StreakViewHolder, position: Int) {
        val currentItem = getItem(position)
        with(holder.binding) {
            dayText.text = currentItem.day
            dayCircle.setCardBackgroundColor(
                ContextCompat.getColor(
                    root.context,
                    if (currentItem.isSelected) R.color.orange_variant else R.color.surface
                )
            )
            flameIcon.visibility = if (currentItem.isSelected) View.VISIBLE else View.GONE

            root.setOnClickListener {
                listener.onClickDay(position)
            }
        }
    }

    class StreakViewHolder(val binding: ItemStreakBinding) :
        RecyclerView.ViewHolder(binding.root)

    class StreakDiffCallback : DiffUtil.ItemCallback<StreakDayUiState>() {
        override fun areItemsTheSame(oldItem: StreakDayUiState, newItem: StreakDayUiState): Boolean {
            return oldItem.day == newItem.day
        }

        override fun areContentsTheSame(oldItem: StreakDayUiState, newItem: StreakDayUiState): Boolean {
            return oldItem == newItem
        }
    }
}