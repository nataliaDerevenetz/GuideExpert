package com.example.GuideExpert.domain.impl

import com.example.GuideExpert.domain.GetAuthTokenByYandexUseCase
import com.example.GuideExpert.domain.models.ProfileYandex
import com.example.GuideExpert.domain.models.UIResources
import com.example.GuideExpert.domain.repository.ExcursionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAuthTokenByYandexUseCaseImpl @Inject constructor(
    private val excursionsRepository: ExcursionsRepository
): GetAuthTokenByYandexUseCase {
    override suspend operator fun invoke(oauthToken: String): Flow<UIResources<ProfileYandex>> {
        return excursionsRepository.getAuthTokenByYandex(oauthToken)
    }
}
