package com.istanbul.qurio.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.istanbul.qurio.R
import com.istanbul.qurio.databinding.ItemStreakBinding

class StreakDayAdapter(
    private val items: MutableList<StreakDayUiState>,
    private val listener: StreakDayInteractionListener
) : RecyclerView.Adapter<StreakDayAdapter.StreakViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreakViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_streak, parent, false)
        return StreakViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: StreakViewHolder, position: Int) {
        val currentItem = items[position]
        holder.binding.apply {
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

    class StreakViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val binding = ItemStreakBinding.bind(viewItem)
    }
}