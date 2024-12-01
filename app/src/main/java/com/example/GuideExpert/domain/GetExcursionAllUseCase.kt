package com.example.GuideExpert.domain

import com.example.GuideExpert.data.Excursion
import kotlinx.coroutines.flow.Flow

interface GetExcursionAllUseCase {
    operator fun invoke(): Flow<List<Excursion>>
}