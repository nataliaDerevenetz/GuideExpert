package com.example.core.domain.impl

import com.example.core.domain.GetExcursionFavoriteUseCase
import com.example.core.domain.repository.ExcursionsRepository
import com.example.core.domain.repository.ProfileRepository
import com.example.core.models.Excursion
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExcursionFavoriteUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val excursionsRepository: ExcursionsRepository
): GetExcursionFavoriteUseCase {
    override suspend operator fun invoke(): Flow<UIResources<List<Excursion>?>> {
        val profile = profileRepository.profileFlow.value
        return excursionsRepository.fetchExcursionsFavorite(profile)
    }
}