package com.example.GuideExpert.domain.impl

import com.example.GuideExpert.domain.GetConfigUseCase
import com.example.GuideExpert.domain.models.Config
import com.example.GuideExpert.domain.models.UIResources
import com.example.GuideExpert.domain.repository.ExcursionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConfigUseCaseImpl @Inject constructor(
    private val excursionsRepository: ExcursionsRepository
): GetConfigUseCase {
    override suspend operator fun invoke(): Flow<UIResources<Config>> {
        return excursionsRepository.getConfigInfo()
    }
}
