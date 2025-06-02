package com.example.core.data.models

import com.example.core.database.models.ExcursionFilterEntity
import com.example.core.database.models.ExcursionFilterWithData
import com.example.core.database.models.ExcursionSearchEntity
import com.example.core.database.models.ExcursionSearchWithData
import com.example.core.database.models.ExcursionsFavoriteEntity
import com.example.core.database.models.ExcursionsFavoriteWithData
import com.example.core.database.models.toImage
import com.example.core.models.Excursion
import com.example.core.network.pojo.ExcursionPOJO

fun ExcursionFilterEntity.toExcursion() =
    Excursion(id, title, description, images, idSort)

fun ExcursionFilterWithData.toExcursion() = Excursion(
    id = excursion.id,
    title = excursion.title,
    description = excursion.description,
    idSort = excursion.idSort,
    images = images.map { it.toImage() })

fun ExcursionSearchEntity.toExcursion() =
    Excursion(id, title, description, images, idSort)


fun ExcursionSearchWithData.toExcursion() =
    Excursion(
        id = excursion.id,
        title = excursion.title,
        description = excursion.description,
        images = images.map { it.toImage() },
        idSort = excursion.idSort
    )

fun ExcursionPOJO.toExcursion() = Excursion(
    id = id,
    title = title,
    description = description,
    images = images.map { it.toImage() },
    idSort = idSort ?: 0,
    timestamp = timestamp ?: 0
)

fun Excursion.toExcursionPOJO() = ExcursionPOJO(id,title, description,images.map{ it.toImagePOJO()},idSort ?: 0, timestamp = timestamp ?: 0)

fun ExcursionPOJO.toExcursionSearchEntity() =
    ExcursionSearchEntity(
        id,
        title,
        description,
        idSort ?: 0,
        images.map { it.toImage() })

fun ExcursionPOJO.toExcursionFilterEntity() =
    ExcursionFilterEntity(
        id,
        title,
        description,
        idSort ?: 0,
        images.map { it.toImage() })


fun ExcursionPOJO.toExcursionSearchWithData() =
    ExcursionSearchWithData(
        ExcursionSearchEntity(
            id,
            title,
            description,
            idSort ?: 0,
            images.map { it.toImage() }),
        images.map { it.toImagePreviewSearchEntity() }
    )

fun ExcursionPOJO.toExcursionFilterWithData() =
    ExcursionFilterWithData(
        ExcursionFilterEntity(
            id,
            title,
            description,
            idSort ?: 0,
            images.map { it.toImage() }),
        images.map { it.toImagePreviewFilterEntity() }
    )

fun ExcursionPOJO.toExcursionsFavoriteWithData() =
    ExcursionsFavoriteWithData(
        ExcursionsFavoriteEntity(
            id = id,
            title = title,
            description = description,
            timestamp = timestamp ?: 0
        ), images = images.map { it.toImagePreviewFavoriteEntity() })

fun ExcursionsFavoriteWithData.toExcursion() =
    Excursion(
        id = excursion.id,
        title = excursion.title,
        description = excursion.description,
        images = images.map { it.toImage() },
        timestamp = excursion.timestamp
    )

fun Excursion.toExcursionsFavoriteWithData() =
    ExcursionsFavoriteWithData(
        excursion = ExcursionsFavoriteEntity(
            id = id,
            title = title,
            description = description,
            timestamp = timestamp
        ), images = images.map {it.toImagePreviewFavoriteEntity() })

fun Excursion.toExcursionsFavoriteEntity() =
    ExcursionsFavoriteEntity(
        id = id,
        title = title,
        description = description,
        timestamp = timestamp
    )
