package com.example.core.domain.impl

import com.example.core.domain.LogoutProfileUseCase
import com.example.core.domain.repository.ProfileRepository
import javax.inject.Inject

class LogoutProfileUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository
): LogoutProfileUseCase {
    override suspend operator fun invoke() {
        profileRepository.removeProfile()
    }
}