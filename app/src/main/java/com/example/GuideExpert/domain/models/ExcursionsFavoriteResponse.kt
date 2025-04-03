package com.example.GuideExpert.domain.models

data class ExcursionsFavoriteResponse(
    val success: Boolean = false,
    val message: String ="",
    val excursions:List<ExcursionFavorite> = listOf()
)

data class ExcursionFavorite(
    val id: Int,
    val excursionId: Int,
    val timestamp: Int
)