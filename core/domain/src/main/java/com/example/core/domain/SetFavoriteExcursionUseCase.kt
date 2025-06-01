package com.example.core.domain

import com.example.core.models.Excursion
import com.example.core.models.SetFavoriteExcursionResponse
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow

interface SetFavoriteExcursionUseCase {
    suspend operator fun invoke(excursion: com.example.core.models.Excursion) : Flow<com.example.core.models.UIResources<com.example.core.models.SetFavoriteExcursionResponse>>
}