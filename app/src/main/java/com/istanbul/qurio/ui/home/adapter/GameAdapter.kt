package com.istanbul.qurio.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.istanbul.qurio.R
import com.istanbul.qurio.databinding.GameCardBinding
import com.istanbul.qurio.ui.home.GameCategoryUIModel

class GameAdapter(
    private val onClick: (GameCategoryUIModel) -> Unit
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    private val items = mutableListOf<GameCategoryUIModel>()

    fun submitList(list: List<GameCategoryUIModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class GameViewHolder(private val binding: GameCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GameCategoryUIModel) {
            binding.gameTitle.text = item.name

            Glide.with(binding.root)
                .load(item.iconUrl)
                .placeholder(R.drawable.im_music)
                .error(R.drawable.im_music)
                .into(binding.gameImage)

            binding.gameCard.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GameCardBinding.inflate(inflater, parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size
}