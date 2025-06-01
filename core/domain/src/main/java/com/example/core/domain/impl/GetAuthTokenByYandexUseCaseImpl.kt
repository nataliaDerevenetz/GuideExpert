package com.example.core.domain.impl

import com.example.core.domain.GetAuthTokenByYandexUseCase
import com.example.core.models.ProfileYandex
import com.example.core.models.UIResources
import com.example.core.domain.repository.DataSourceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAuthTokenByYandexUseCaseImpl @Inject constructor(
    private val dataSourceRepository: DataSourceRepository
): GetAuthTokenByYandexUseCase {
    override suspend operator fun invoke(oauthToken: String): Flow<com.example.core.models.UIResources<com.example.core.models.ProfileYandex>> {
        return dataSourceRepository.getAuthTokenByYandex(oauthToken)
    }
}
