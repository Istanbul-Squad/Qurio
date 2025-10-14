package com.istanbul.qurio.game_card

import android.content.Context
import androidx.core.content.ContextCompat.getString
import com.istanbul.qurio.R

object CategoriesList {
    fun getCategories(context: Context) =
        listOf(
            GameCardUiState(
                gameId = 1.toString(),
                gameCategory = getString(context, R.string.category_title_geography),
                imageRes = R.drawable.im_geography,
            ),
            GameCardUiState(
                gameId = 2.toString(),
                gameCategory = getString(context, R.string.category_title_history),
                imageRes = R.drawable.im_history,
            ),
            GameCardUiState(
                gameId = 3.toString(),
                gameCategory = getString(context, R.string.category_title_society_culture),
                imageRes = R.drawable.im_society_culture,
            ),
            GameCardUiState(
                gameId = 4.toString(),
                gameCategory = getString(context, R.string.category_title_general_knowledge),
                imageRes = R.drawable.im_general_knowledge,
            ),
            GameCardUiState(
                gameId = 5.toString(),
                gameCategory = getString(context, R.string.category_title_music),
                imageRes = R.drawable.im_music,
            ),
            GameCardUiState(
                gameId = 6.toString(),
                gameCategory = getString(context, R.string.category_title_sport_leisure),
                imageRes = R.drawable.im_sport_leisure,
            ),
            GameCardUiState(
                gameId = 7.toString(),
                gameCategory = getString(context, R.string.category_title_film_tv),
                imageRes = R.drawable.im_film_tv,
            ),
            GameCardUiState(
                gameId = 8.toString(),
                gameCategory = getString(context, R.string.category_title_arts_literature),
                imageRes = R.drawable.im_art,
            ),
            GameCardUiState(
                gameId = 9.toString(),
                gameCategory = getString(context, R.string.category_title_food_drink),
                imageRes = R.drawable.im_food,
            ),
            GameCardUiState(
                gameId = 10.toString(),
                gameCategory = getString(context, R.string.category_title_science),
                imageRes = R.drawable.im_science,
            ),
        )
}