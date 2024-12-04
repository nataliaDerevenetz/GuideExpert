package com.example.GuideExpert.domain

import com.example.GuideExpert.domain.models.Excursion

interface GetAllExcursionsUseCase {
   // operator fun invoke(): Flow<List<Excursion>>
   operator fun invoke(): List<Excursion>
}