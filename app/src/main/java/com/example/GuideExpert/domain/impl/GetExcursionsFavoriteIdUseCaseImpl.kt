package com.example.GuideExpert.domain.impl

import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.domain.GetExcursionsFavoriteIdUseCase
import com.example.GuideExpert.domain.models.ExcursionsFavoriteIdResponse
import com.example.GuideExpert.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExcursionsFavoriteIdUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository
): GetExcursionsFavoriteIdUseCase {
    override suspend operator fun invoke(): Flow<UIResources<ExcursionsFavoriteIdResponse>> {
        return profileRepository.getIdExcursionsFavorite()
    }
}