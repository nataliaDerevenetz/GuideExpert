package com.example.core.domain

import com.example.core.models.Excursion
import com.example.core.models.SetFavoriteExcursionResponse
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow

interface SetFavoriteExcursionUseCase {
    suspend operator fun invoke(excursion: Excursion) : Flow<UIResources<SetFavoriteExcursionResponse>>
}