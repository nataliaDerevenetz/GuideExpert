package com.example.GuideExpert.domain.models

import com.example.GuideExpert.R
import kotlinx.serialization.Serializable

class ExcursionData(
    val excursionId: String = "8886",
    val title: String ="Супер экскурсия!!",
    val company: String = "Лучшая компания",
    val photo: Int = R.drawable.excurs1
)