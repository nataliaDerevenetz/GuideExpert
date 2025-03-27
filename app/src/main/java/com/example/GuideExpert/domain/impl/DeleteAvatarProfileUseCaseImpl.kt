package com.example.GuideExpert.domain.impl

import com.example.GuideExpert.domain.DeleteAvatarProfileUseCase
import com.example.GuideExpert.domain.LogoutProfileUseCase
import com.example.GuideExpert.domain.repository.ProfileRepository
import javax.inject.Inject

class DeleteAvatarProfileUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository
): DeleteAvatarProfileUseCase {
    override suspend operator fun invoke() {
        profileRepository.removeAvatarProfile()
    }
}