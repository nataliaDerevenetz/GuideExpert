package com.example.core.domain.impl

import com.example.core.domain.UpdateAvatarProfileUseCase
import com.example.core.models.Avatar
import com.example.core.models.UIResources
import com.example.core.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject


class UpdateAvatarProfileUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository
): UpdateAvatarProfileUseCase {
    override suspend operator fun invoke(imagePart: MultipartBody.Part): Flow<com.example.core.models.UIResources<com.example.core.models.Avatar>> {
        return profileRepository.updateAvatarProfile(imagePart)
    }
}