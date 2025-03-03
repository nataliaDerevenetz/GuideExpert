package com.example.GuideExpert.data.mappers

import com.example.GuideExpert.data.local.models.ImagePreviewSearchEntity
import com.example.GuideExpert.data.remote.pojo.ExcursionDataPOJO
import com.example.GuideExpert.data.remote.pojo.ImagePOJO
import com.example.GuideExpert.domain.models.Banner
import com.example.GuideExpert.domain.models.ExcursionData
import com.example.GuideExpert.domain.models.Image

fun ExcursionDataPOJO.toExcursionData() = ExcursionData(excursionId = excursionId,
    title = title, owner = owner,text = text, description = description,
    group = group, images = images.map{it.toImage()})

fun ImagePOJO.toImage() = Image(id,excursionId,url)

fun Image.toImagePOJO() = ImagePOJO(id,excursionId,url)

fun ImagePOJO.toImagePreviewSearchEntity() = ImagePreviewSearchEntity(
    id = id, excursionId = excursionId, url = url
)
