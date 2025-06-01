package com.example.core.domain

import com.example.core.models.ProfileYandex
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow

interface GetAuthTokenByYandexUseCase {
    suspend operator fun invoke(oauthToken: String) : Flow<com.example.core.models.UIResources<com.example.core.models.ProfileYandex>>
}