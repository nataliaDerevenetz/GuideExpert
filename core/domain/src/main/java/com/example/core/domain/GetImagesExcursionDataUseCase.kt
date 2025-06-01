package com.example.core.domain

import com.example.core.models.Image
import kotlinx.coroutines.flow.Flow

interface GetImagesExcursionDataUseCase {
    operator fun invoke(excursionId: Int) : Flow<List<com.example.core.models.Image>>
}