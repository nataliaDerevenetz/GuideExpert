package com.example.core.domain

import com.example.core.models.Excursion
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow

interface GetExcursionFavoriteUseCase {
    suspend operator fun invoke(): Flow<UIResources<List<Excursion>?>>
}