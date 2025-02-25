package com.example.GuideExpert.data.mappers

import com.example.GuideExpert.data.local.models.ExcursionDataEntity
import com.example.GuideExpert.data.local.models.ImageEntity
import com.example.GuideExpert.domain.models.ExcursionData
import com.example.GuideExpert.domain.models.Image

fun ExcursionData.toExcursionDataEntity() = ExcursionDataEntity(
    id = excursionId,title = title, description = description,text = text,owner=owner,group=group
)

fun Image.toImageEntity() = ImageEntity(
    id = id, excursionId = excursionId, url = url
)

fun ExcursionDataEntity.toExcursionData() = ExcursionData(
    excursionId = id,title = title, description = description,text = text,owner=owner,group=group
)

fun ImageEntity.toImage() = Image(
    id = id, excursionId = excursionId, url = url
)
