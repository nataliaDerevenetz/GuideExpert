package com.example.core.domain.impl

import com.example.core.domain.GetProfileUseCase
import com.example.core.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository
): GetProfileUseCase {
    override suspend operator fun invoke() {
        profileRepository.fetchProfile()
    }
}