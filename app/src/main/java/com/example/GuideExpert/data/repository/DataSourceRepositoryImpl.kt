package com.example.GuideExpert.data.repository

import com.example.GuideExpert.data.models.toConfig
import com.example.GuideExpert.data.models.toProfileYandex
import com.example.GuideExpert.data.remote.services.DataSourceService
import com.example.core.models.Config
import com.example.core.models.ProfileYandex
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataSourceRepositoryImpl @Inject constructor(
    private val dataSourceService: DataSourceService,
): com.example.core.domain.repository.DataSourceRepository {
    override suspend fun getConfigInfo(): Flow<UIResources<Config>> = flow<UIResources<Config>> {
        emit(UIResources.Loading)
        val result = dataSourceService.getConfig()
        if (result.isSuccessful) {
            val config = result.body()?.toConfig() ?: Config()
            emit(UIResources.Success(config))
        }
    }.catch { e->
        emit(UIResources.Error(e.localizedMessage ?: "Unknown error"))
    }

    override fun getAuthTokenByYandex(oauthToken:String): Flow<UIResources<ProfileYandex>> = flow<UIResources<ProfileYandex>>{
        emit(UIResources.Loading)
        val result = dataSourceService.loginYandex(oauthToken)
        if (result.isSuccessful) {
            val profile = result.body()?.toProfileYandex() ?: ProfileYandex()
            emit(UIResources.Success(profile))
        } else {
            emit(UIResources.Error("Unknown error"))
        }
    }.catch { e->
        emit(UIResources.Error(e.localizedMessage ?: "Unknown error"))
    }

}