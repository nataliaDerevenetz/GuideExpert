package com.example.core.domain

import com.example.core.models.Image
import kotlinx.coroutines.flow.Flow

interface GetImageExcursionUseCase {
    operator fun invoke(imageId: Int) : Flow<com.example.core.models.Image>
}