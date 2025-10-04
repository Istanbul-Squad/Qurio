package com.istanbul.qurio.game_card

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.istanbul.qurio.R
import com.istanbul.qurio.databinding.GameCardBinding

class GameCardAdapter(
    private val items: MutableList<GameCardUiState>,
    private val listener: GameCardInteractionListener
) : RecyclerView.Adapter<GameCardAdapter.GameCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_card, parent, false)
        return GameCardViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: GameCardViewHolder, position: Int) {
        val currentItem = items[position]
        holder.binding.apply {
            gameTitle.text = currentItem.gameTitle
            gameImage.setImageResource(currentItem.imageRes)
            //TODO make the border dynamic based on category or someThing
            root.setOnClickListener {
                listener.onPlayGameClick()
            }
        }
    }


    class GameCardViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val binding = GameCardBinding.bind(viewItem)
    }
}