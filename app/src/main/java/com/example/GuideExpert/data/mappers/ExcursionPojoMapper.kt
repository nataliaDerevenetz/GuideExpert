package com.example.GuideExpert.data.mappers

import com.example.GuideExpert.data.local.models.ExcursionEntity
import com.example.GuideExpert.data.remote.pojo.ExcursionPOJO
import com.example.GuideExpert.domain.models.Excursion

fun ExcursionPOJO.toExcursion() = Excursion(id,title, description,photo)

fun Excursion.toExcursionPOJO() = ExcursionPOJO(id,title, description,photo)

fun ExcursionPOJO.toExcursionEntity() = ExcursionEntity(id,title, description,photo)
