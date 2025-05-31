package com.example.GuideExpert.data.local.models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.GuideExpert.data.mappers.toImage

data class ExcursionSearchWithData(
    @Embedded val excursion: ExcursionSearchEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "excursionId")
    val images: List<ImagePreviewSearchEntity>
)

fun ExcursionSearchWithData.toExcursionSearchEntity() = ExcursionSearchEntity(id = excursion.id,
    title = excursion.title, description = excursion.description, idSort = excursion.idSort, images = images.map { it.toImage() }
)
