package com.example.core.domain.impl

import com.example.core.domain.LoadExcursionFavoriteUseCase
import com.example.core.models.Excursion
import com.example.core.models.UIResources
import com.example.core.domain.repository.ExcursionsRepository
import com.example.core.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadExcursionFavoriteUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val excursionsRepository: ExcursionsRepository
): LoadExcursionFavoriteUseCase {
    override suspend operator fun invoke(): Flow<com.example.core.models.UIResources<List<com.example.core.models.Excursion>?>> {
        val profile = profileRepository.profileFlow.value
        return excursionsRepository.fetchExcursionsFavorite(profile)
    }
}