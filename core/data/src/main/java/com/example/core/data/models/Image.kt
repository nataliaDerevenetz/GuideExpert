package com.example.core.data.models

import com.example.core.database.models.ImageEntity
import com.example.core.database.models.ImagePreviewFavoriteEntity
import com.example.core.database.models.ImagePreviewFilterEntity
import com.example.core.database.models.ImagePreviewSearchEntity
import com.example.core.models.Image
import com.example.core.network.pojo.ImagePOJO

fun Image.toImageEntity() = ImageEntity(
    id = id, excursionId = excursionId, url = url
)


fun ImageEntity.toImage() = Image(
    id = id, excursionId = excursionId, url = url
)

fun ImagePOJO.toImage() = Image(id, excursionId, url)

fun Image.toImagePOJO() = ImagePOJO(id,excursionId,url)

fun ImagePOJO.toImagePreviewSearchEntity() =
    ImagePreviewSearchEntity(
        id = id, excursionId = excursionId, url = url
    )

fun ImagePOJO.toImagePreviewFilterEntity() =
    ImagePreviewFilterEntity(
        id = id, excursionId = excursionId, url = url
    )

fun ImagePOJO.toImagePreviewFavoriteEntity() =
    ImagePreviewFavoriteEntity(
        id = id, excursionId = excursionId, url = url
    )

fun Image.toImagePreviewFavoriteEntity() =
    ImagePreviewFavoriteEntity(
        id = id, excursionId = excursionId, url = url
    )