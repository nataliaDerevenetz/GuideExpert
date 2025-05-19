package com.example.GuideExpert.domain.impl

import com.example.GuideExpert.domain.DeleteFavoriteExcursionUseCase
import com.example.GuideExpert.domain.models.DeleteFavoriteExcursionResponse
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.UIResources
import com.example.GuideExpert.domain.repository.DataSourceRepository
import com.example.GuideExpert.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFavoriteExcursionUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val dataSourceRepository: DataSourceRepository
): DeleteFavoriteExcursionUseCase {
    override suspend operator fun invoke(excursion: Excursion): Flow<UIResources<DeleteFavoriteExcursionResponse>> {
        val profile = profileRepository.profileFlow.value
        return dataSourceRepository.removeFavoriteExcursion(excursion,profile)
    }
}