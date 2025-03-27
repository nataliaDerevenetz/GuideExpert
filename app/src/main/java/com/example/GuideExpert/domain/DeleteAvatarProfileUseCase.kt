package com.example.GuideExpert.domain

import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.domain.models.Avatar
import com.example.GuideExpert.domain.models.RemoveAvatarProfileResponse
import kotlinx.coroutines.flow.Flow

interface DeleteAvatarProfileUseCase {
    suspend operator fun invoke() : Flow<UIResources<RemoveAvatarProfileResponse>>
}