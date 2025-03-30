package com.example.GuideExpert.domain

import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.domain.models.ExcursionsFavoriteIdResponse
import kotlinx.coroutines.flow.Flow

interface GetExcursionsFavoriteIdUseCase {
    suspend operator fun invoke() : Flow<UIResources<ExcursionsFavoriteIdResponse>>
}