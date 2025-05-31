package com.example.GuideExpert.data.models

import com.example.GuideExpert.data.local.models.ExcursionDataEntity
import com.example.GuideExpert.data.remote.pojo.ExcursionDataPOJO
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ExcursionData

fun ExcursionData.toExcursionDataEntity() = ExcursionDataEntity(
    id = excursionId,title = title, description = description,text = text,owner=owner,group=group
)

fun ExcursionDataEntity.toExcursionData() = ExcursionData(
    excursionId = id,title = title, description = description,text = text,owner=owner,group=group
)

fun ExcursionDataPOJO.toExcursionData() = ExcursionData(excursionId = excursionId,
    title = title, owner = owner,text = text, description = description,
    group = group, images = images.map{it.toImage()})

fun ExcursionData.toExcursion() = Excursion(id = excursionId,title= title,description = description,
    images= images)
