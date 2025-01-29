package com.example.GuideExpert.data.mappers

import com.example.GuideExpert.data.local.models.ExcursionFilterEntity
import com.example.GuideExpert.domain.models.Excursion

fun ExcursionFilterEntity.toExcursion() = Excursion(id,title, description,photo)
