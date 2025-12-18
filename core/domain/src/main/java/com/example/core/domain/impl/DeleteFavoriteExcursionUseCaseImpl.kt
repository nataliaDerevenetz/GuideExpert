package com.example.core.domain.impl

import com.example.core.domain.DeleteFavoriteExcursionUseCase
import com.example.core.models.DeleteFavoriteExcursionResponse
import com.example.core.models.Excursion
import com.example.core.models.UIResources
import com.example.core.domain.repository.ExcursionsRepository
import com.example.core.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFavoriteExcursionUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val excursionsRepository: ExcursionsRepository
): DeleteFavoriteExcursionUseCase {
    override suspend operator fun invoke(excursion: Excursion): Flow<UIResources<DeleteFavoriteExcursionResponse>> {
        val profile = profileRepository.profileFlow.value
        return excursionsRepository.removeFavoriteExcursion(excursion,profile)
    }
}