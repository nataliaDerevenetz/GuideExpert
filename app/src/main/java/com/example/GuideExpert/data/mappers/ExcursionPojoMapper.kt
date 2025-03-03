package com.example.GuideExpert.data.mappers

import com.example.GuideExpert.data.local.models.ExcursionSearchEntity
import com.example.GuideExpert.data.local.models.ExcursionFilterEntity
import com.example.GuideExpert.data.local.models.ExcursionSearchWithData
import com.example.GuideExpert.data.remote.pojo.ExcursionPOJO
import com.example.GuideExpert.domain.models.Excursion

fun ExcursionPOJO.toExcursion() = Excursion(id,title, description,images.map { it.toImage() })

fun Excursion.toExcursionPOJO() = ExcursionPOJO(id,title, description,images.map{ it.toImagePOJO()})

fun ExcursionPOJO.toExcursionSearchEntity() = ExcursionSearchEntity(id,title, description,images.map { it.toImage() })

fun ExcursionPOJO.toExcursionFilterEntity() = ExcursionFilterEntity(id,title, description,images.map { it.toImage() })


fun ExcursionPOJO.toExcursionSearchWithData() = ExcursionSearchWithData(
    ExcursionSearchEntity(id,title, description,images.map{it.toImage()}),
    images.map{ it.toImagePreviewSearchEntity() }
)
