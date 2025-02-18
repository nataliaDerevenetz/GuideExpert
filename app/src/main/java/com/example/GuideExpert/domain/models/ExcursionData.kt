package com.example.GuideExpert.domain.models

import com.example.GuideExpert.R

class ExcursionData(
    val excursionId: String = "8886",
    val title: String ="Супер экскурсия!!",
    val owner: String = "Лучшая компания",
    val photo: Int = R.drawable.excurs1,
    val text: String = "",
    val description: String = "",
    val group: Int = 1,
    val photos: List<String> = listOf(),
)