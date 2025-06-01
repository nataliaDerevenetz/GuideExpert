package com.example.core.domain

import com.example.core.models.Excursion
import kotlinx.coroutines.flow.Flow

interface GetExcursionFavoriteUseCase {
    operator fun invoke(): Flow<List<com.example.core.models.Excursion>>
}