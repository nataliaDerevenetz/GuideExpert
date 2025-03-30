package com.example.GuideExpert.domain.models

data class ExcursionsFavoriteIdResponse(
    val success: Boolean = false,
    val message: String ="",
    val excursions:List<Int> = listOf()
)
