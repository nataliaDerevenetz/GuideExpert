package com.example.GuideExpert.domain

import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.domain.models.MessageResponse
import com.example.GuideExpert.domain.models.SetFavoriteExcursionResponse
import kotlinx.coroutines.flow.Flow

interface SetFavoriteExcursionUseCase {
    suspend operator fun invoke(excursionId: Int) : Flow<UIResources<SetFavoriteExcursionResponse>>
}