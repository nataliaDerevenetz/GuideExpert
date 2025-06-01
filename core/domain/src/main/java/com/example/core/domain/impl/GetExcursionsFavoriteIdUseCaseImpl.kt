package com.example.core.domain.impl

import com.example.core.domain.GetExcursionsFavoriteIdUseCase
import com.example.core.models.ErrorExcursionsRepository
import com.example.core.domain.repository.ExcursionsRepository
import com.example.core.domain.repository.ProfileRepository
import javax.inject.Inject

class GetExcursionsFavoriteIdUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val excursionsRepository: ExcursionsRepository
): GetExcursionsFavoriteIdUseCase {
    override suspend operator fun invoke() {
        val profile = profileRepository.profileFlow.value
        profile?.let {
            val response  = excursionsRepository.getExcursionsFavorite(profile)
            when(response) {
                com.example.core.models.ErrorExcursionsRepository.Authorization -> {
                    profileRepository.removeProfile()
                    excursionsRepository.removeFavoriteExcursionIds()
                }
                else -> {}
            }
        }
    }
}