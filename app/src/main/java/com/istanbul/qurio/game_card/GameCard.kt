package com.istanbul.qurio.game_card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import com.istanbul.qurio.databinding.GameCardBinding

class GameCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : TiltedCardView(context, attrs) {

    private val binding = GameCardBinding.inflate(LayoutInflater.from(context),this,true)

    fun setState(
        title: String,
        @DrawableRes imageRes: Int,
    ) {
        setTitle(title)
        setImage(imageRes)
    }

    private fun setTitle(title: String) {
        binding.gameTitle.text = title
    }

    private fun setImage(@DrawableRes imageRes: Int) {
        binding.gameImage.setImageResource(imageRes)
    }

}