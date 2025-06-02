package com.example.core.data.models

import com.example.core.database.models.ExcursionDataEntity
import com.example.core.models.Excursion
import com.example.core.models.ExcursionData
import com.example.core.network.pojo.ExcursionDataPOJO

fun ExcursionData.toExcursionDataEntity() = ExcursionDataEntity(
    id = excursionId,
    title = title,
    description = description,
    text = text,
    owner = owner,
    group = group
)

fun ExcursionDataEntity.toExcursionData() = ExcursionData(
    excursionId = id,
    title = title,
    description = description,
    text = text,
    owner = owner,
    group = group
)

fun ExcursionDataPOJO.toExcursionData() = ExcursionData(
    excursionId = excursionId,
    title = title, owner = owner, text = text, description = description,
    group = group, images = images.map { it.toImage() })

fun ExcursionData.toExcursion() =
    Excursion(
        id = excursionId, title = title, description = description,
        images = images
    )
