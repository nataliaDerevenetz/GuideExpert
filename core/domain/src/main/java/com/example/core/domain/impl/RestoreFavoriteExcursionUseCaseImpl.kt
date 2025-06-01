package com.example.core.domain.impl

import com.example.core.domain.RestoreFavoriteExcursionUseCase
import com.example.core.models.Excursion
import com.example.core.models.RestoreFavoriteExcursionResponse
import com.example.core.models.UIResources
import com.example.core.domain.repository.ExcursionsRepository
import com.example.core.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RestoreFavoriteExcursionUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val excursionsRepository: ExcursionsRepository
): RestoreFavoriteExcursionUseCase {
    override suspend operator fun invoke(excursion: com.example.core.models.Excursion): Flow<com.example.core.models.UIResources<com.example.core.models.RestoreFavoriteExcursionResponse>> {
        val profile = profileRepository.profileFlow.value
        return excursionsRepository.restoreFavoriteExcursion(excursion,profile)
    }
}