package com.example.GuideExpert.domain.impl

import com.example.GuideExpert.domain.GetExcursionsFavoriteIdUseCase
import com.example.GuideExpert.domain.models.ErrorExcursionsRepository
import com.example.GuideExpert.domain.repository.DataSourceRepository
import com.example.GuideExpert.domain.repository.ProfileRepository
import javax.inject.Inject

class GetExcursionsFavoriteIdUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val dataSourceRepository: DataSourceRepository
): GetExcursionsFavoriteIdUseCase {
    override suspend operator fun invoke() {
        val profile = profileRepository.profileFlow.value
        profile?.let {
            val response  = dataSourceRepository.getExcursionsFavorite(profile)
            when(response) {
                ErrorExcursionsRepository.Authorization -> {
                    profileRepository.removeProfile()
                    dataSourceRepository.removeFavoriteExcursionIds()
                }
                else -> {}
            }
        }
    }
}