package com.example.core.domain.repository

import com.example.core.models.Config
import com.example.core.models.ProfileYandex
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow

interface DataSourceRepository {
    suspend fun getConfigInfo(): Flow<UIResources<Config>>
    fun getAuthTokenByYandex(oauthToken: String): Flow<UIResources<ProfileYandex>>
}