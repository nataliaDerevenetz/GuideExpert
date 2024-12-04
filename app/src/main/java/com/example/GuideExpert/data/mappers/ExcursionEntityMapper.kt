package com.example.GuideExpert.data.mappers

import com.example.GuideExpert.data.storage.models.ExcursionEntity
import com.example.GuideExpert.domain.models.Excursion

fun ExcursionEntity.toExcursion() = Excursion(id,name, description,photo)

fun Excursion.toExcursionEntity() = ExcursionEntity(id,name, description,photo)
