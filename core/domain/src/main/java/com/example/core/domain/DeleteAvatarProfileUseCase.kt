package com.example.core.domain

import com.example.core.models.MessageResponse
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow

interface DeleteAvatarProfileUseCase {
    suspend operator fun invoke() : Flow<com.example.core.models.UIResources<com.example.core.models.MessageResponse>>
}