package com.example.feature.home.utils

import com.example.core.models.Excursion
import com.example.core.models.ExcursionData

fun ExcursionData.toExcursion() =
    Excursion(
        id = excursionId, title = title, description = description,
        images = images
    )