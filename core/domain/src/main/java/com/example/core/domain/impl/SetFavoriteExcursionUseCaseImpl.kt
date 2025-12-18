package com.example.core.domain.impl

import com.example.core.domain.SetFavoriteExcursionUseCase
import com.example.core.models.Excursion
import com.example.core.models.SetFavoriteExcursionResponse
import com.example.core.models.UIResources
import com.example.core.domain.repository.ExcursionsRepository
import com.example.core.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetFavoriteExcursionUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val excursionsRepository: ExcursionsRepository
): SetFavoriteExcursionUseCase {
    override suspend operator fun invoke(excursion: Excursion): Flow<UIResources<SetFavoriteExcursionResponse>> {
        val profile = profileRepository.profileFlow.value
        return excursionsRepository.setFavoriteExcursion(excursion,profile)
    }
}