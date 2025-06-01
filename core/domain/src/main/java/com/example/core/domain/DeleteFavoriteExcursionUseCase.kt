package com.example.core.domain

import com.example.core.models.DeleteFavoriteExcursionResponse
import com.example.core.models.Excursion
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow

interface DeleteFavoriteExcursionUseCase {
    suspend operator fun invoke(excursion: com.example.core.models.Excursion) : Flow<com.example.core.models.UIResources<com.example.core.models.DeleteFavoriteExcursionResponse>>
}