package com.example.core.models

data class SetFavoriteExcursionResponse(
    val success: Boolean = false,
    val message: String = "",
    val excursion: ExcursionFavorite? = null,
)
