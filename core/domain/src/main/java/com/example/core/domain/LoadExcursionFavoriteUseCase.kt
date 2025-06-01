package com.example.core.domain

import com.example.core.models.Excursion
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow

interface LoadExcursionFavoriteUseCase {
    suspend operator fun invoke(): Flow<com.example.core.models.UIResources<List<com.example.core.models.Excursion>?>>
}