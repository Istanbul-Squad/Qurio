package com.istanbul.qurio.ui.home

import com.istanbul.qurio.model.GameCategory

fun GameCategory.toUIModel() = GameCategoryUIModel(
    id = id,
    name = name,
    iconUrl = iconUrl
)