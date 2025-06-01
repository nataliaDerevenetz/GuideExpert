package com.example.core.domain

import com.example.core.models.ExcursionData
import kotlinx.coroutines.flow.Flow

interface GetExcursionDataUseCase {
    operator fun invoke(excursionId: Int) : Flow<com.example.core.models.ExcursionData?>
}