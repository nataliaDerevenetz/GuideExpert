package com.example.GuideExpert.data.mappers

import com.example.GuideExpert.data.local.models.ExcursionSearchEntity
import com.example.GuideExpert.data.remote.pojo.ExcursionPOJO
import com.example.GuideExpert.domain.models.Excursion

fun ExcursionSearchEntity.toExcursion() = Excursion(id,title, description,photo)

fun Excursion.toExcursionSearchEntity() = ExcursionSearchEntity(id,title, description,photo)

fun ExcursionSearchEntity.toExcursionPOJO() = ExcursionPOJO(id,title, description,photo)
