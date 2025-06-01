package com.example.core.domain

import com.example.core.models.Config
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow

interface GetConfigUseCase {
    suspend operator fun invoke() : Flow<com.example.core.models.UIResources<com.example.core.models.Config>>
}