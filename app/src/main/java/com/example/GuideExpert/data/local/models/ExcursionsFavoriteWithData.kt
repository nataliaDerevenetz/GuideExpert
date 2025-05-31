package com.example.GuideExpert.data.local.models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.GuideExpert.data.mappers.toImage

data class ExcursionsFavoriteWithData(
    @Embedded val excursion: ExcursionsFavoriteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "excursionId")
    val images: List<ImagePreviewFavoriteEntity>
)

fun ExcursionsFavoriteWithData.toExcursionsFavoriteEntity() = ExcursionsFavoriteEntity(id = excursion.id,
    title = excursion.title, description = excursion.description, timestamp = excursion.timestamp, images = images.map { it.toImage() }
)