package com.example.GuideExpert.data.local.models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.GuideExpert.data.mappers.toImage

data class ExcursionFilterWithData(
    @Embedded val excursion: ExcursionFilterEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "excursionId")
    val images: List<ImagePreviewFilterEntity>
)

fun ExcursionFilterWithData.toExcursionFilterEntity() = ExcursionFilterEntity(id = excursion.id,
    title = excursion.title, description = excursion.description, idSort = excursion.idSort, images = images.map { it.toImage() }
)
