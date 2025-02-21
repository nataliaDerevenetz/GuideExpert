package com.example.GuideExpert.domain.models

import com.example.GuideExpert.R

class ExcursionData(
    val excursionId: Int = 1,
    val title: String ="",
    val owner: Int = 1,
    val photo: Int = R.drawable.excurs1,
    val text: String = "",
    val description: String = "",
    val group: Int = 1,
    val images: List<Image> = listOf(),
)