package com.example.core.domain

import com.example.core.models.ExcursionData
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow

interface GetExcursionDetailUseCase {
     suspend operator fun invoke(excursionId: Int) : Flow<com.example.core.models.UIResources<com.example.core.models.ExcursionData>>
}