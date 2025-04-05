package com.example.GuideExpert.domain

import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.domain.models.DeleteFavoriteExcursionResponse
import kotlinx.coroutines.flow.Flow

interface DeleteFavoriteExcursionUseCase {
    suspend operator fun invoke(excursionId: Int) : Flow<UIResources<DeleteFavoriteExcursionResponse>>
}