package com.example.GuideExpert.domain

import com.example.GuideExpert.domain.models.Excursion
import kotlinx.coroutines.flow.Flow

interface GetExcursionAllUseCase {
   // operator fun invoke(): Flow<List<Excursion>>
   operator fun invoke(): List<Excursion>
}