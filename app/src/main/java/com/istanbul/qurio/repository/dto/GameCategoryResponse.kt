package com.istanbul.qurio.repository.dto

import com.google.gson.annotations.SerializedName
import com.istanbul.qurio.model.GameCategory

data class GameCategoriesResponse(
    @SerializedName("trivia_categories")
    val categories: List<GameCategoryResponse>
)

data class GameCategoryResponse(
    val id: Int,
    val name: String
) {
    fun toDomain(): GameCategory {
        val iconUrl = "https://your-app-icons.com/icons/$id.png"
        return GameCategory(id, name, iconUrl)
    }
}
