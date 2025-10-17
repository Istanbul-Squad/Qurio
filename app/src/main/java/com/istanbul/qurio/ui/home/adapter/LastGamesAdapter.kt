package com.istanbul.qurio.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.istanbul.qurio.databinding.LastGamesBinding
import com.istanbul.qurio.model.QuizResult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LastGamesAdapter :
    RecyclerView.Adapter<LastGamesAdapter.LastGameViewHolder>() {

    private val items = mutableListOf<QuizResult>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<QuizResult>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class LastGameViewHolder(private val binding: LastGamesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: QuizResult) {
            binding.apply {
                //ask
                gameCategory.text ="General Knowledge"
                plusCoins.text = "+${item.coinsEarned}"
                gameStarRate.text = item.correctAnswers.toString()
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                date.text = dateFormat.format(Date(item.timestamp))
                //ask
                gameTime.text = "—"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastGameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LastGamesBinding.inflate(inflater, parent, false)
        return LastGameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LastGameViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size
}