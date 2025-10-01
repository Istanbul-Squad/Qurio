package com.istanbul.qurio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.istanbul.qurio.databinding.ItemStreakBinding

class StreakAdapter(
    private val items: MutableList<StreakUiState>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<StreakAdapter.StreakViewHolder>() {

    inner class StreakViewHolder(val binding: ItemStreakBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreakViewHolder {
        val binding = ItemStreakBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StreakViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: StreakViewHolder, position: Int) {
        holder.binding.streakState = items[position]
        holder.binding.executePendingBindings()
    }
}