package com.example.GuideExpert.data.models

import com.example.GuideExpert.data.local.models.ImageEntity
import com.example.GuideExpert.data.local.models.ImagePreviewFavoriteEntity
import com.example.GuideExpert.data.local.models.ImagePreviewFilterEntity
import com.example.GuideExpert.data.local.models.ImagePreviewSearchEntity
import com.example.GuideExpert.data.remote.pojo.ImagePOJO
import com.example.GuideExpert.domain.models.Image

fun Image.toImageEntity() = ImageEntity(
    id = id, excursionId = excursionId, url = url
)


fun ImageEntity.toImage() = Image(
    id = id, excursionId = excursionId, url = url
)

fun ImagePOJO.toImage() = Image(id,excursionId,url)

fun Image.toImagePOJO() = ImagePOJO(id,excursionId,url)

fun ImagePOJO.toImagePreviewSearchEntity() = ImagePreviewSearchEntity(
    id = id, excursionId = excursionId, url = url
)

fun ImagePOJO.toImagePreviewFilterEntity() = ImagePreviewFilterEntity(
    id = id, excursionId = excursionId, url = url
)

fun ImagePOJO.toImagePreviewFavoriteEntity() = ImagePreviewFavoriteEntity(
    id = id, excursionId = excursionId, url = url
)

fun Image.toImagePreviewFavoriteEntity() = ImagePreviewFavoriteEntity(
    id = id, excursionId = excursionId, url = url
)