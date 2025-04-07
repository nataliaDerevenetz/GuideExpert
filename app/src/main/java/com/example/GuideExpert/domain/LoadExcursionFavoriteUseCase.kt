package com.example.GuideExpert.domain

import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.domain.models.ExcursionsFavorite
import kotlinx.coroutines.flow.Flow

interface LoadExcursionFavoriteUseCase {
    suspend operator fun invoke(): Flow<UIResources<ExcursionsFavorite>>
}