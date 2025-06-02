package com.example.core.domain.impl

import com.example.core.domain.DeleteAvatarProfileUseCase
import com.example.core.models.MessageResponse
import com.example.core.models.UIResources
import com.example.core.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAvatarProfileUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository
): DeleteAvatarProfileUseCase {
    override suspend operator fun invoke(): Flow<UIResources<MessageResponse>> {
        return profileRepository.removeAvatarProfile()
    }
}