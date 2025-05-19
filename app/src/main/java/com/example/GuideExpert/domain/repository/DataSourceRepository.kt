package com.example.GuideExpert.domain.repository

import com.example.GuideExpert.domain.models.Config
import com.example.GuideExpert.domain.models.DeleteFavoriteExcursionResponse
import com.example.GuideExpert.domain.models.ErrorExcursionsRepository
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ExcursionData
import com.example.GuideExpert.domain.models.ExcursionFavorite
import com.example.GuideExpert.domain.models.Image
import com.example.GuideExpert.domain.models.Profile
import com.example.GuideExpert.domain.models.ProfileYandex
import com.example.GuideExpert.domain.models.RestoreFavoriteExcursionResponse
import com.example.GuideExpert.domain.models.SetFavoriteExcursionResponse
import com.example.GuideExpert.domain.models.UIResources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface DataSourceRepository {
    val profileFavoriteExcursionIdFlow: StateFlow<List<ExcursionFavorite>>
    suspend fun getExcursionInfo(excursionId: Int): Flow<UIResources<ExcursionData>>
    suspend fun getConfigInfo(): Flow<UIResources<Config>>
    fun getExcursionData(excursionId: Int): Flow<ExcursionData?>
    fun getImagesExcursion(excursionId: Int): Flow<List<Image>>
    fun getImageExcursion(imageId: Int): Flow<Image>
    fun getAuthTokenByYandex(oauthToken: String): Flow<UIResources<ProfileYandex>>
    fun getExcursionFavoriteFlow(): Flow<List<Excursion>>
    suspend fun getExcursionsFavorite(profile: Profile): ErrorExcursionsRepository?
    suspend fun updateExcursionsFavorite(excursions: List<ExcursionFavorite>)
    suspend fun removeFavoriteExcursionIds()
    suspend fun setFavoriteExcursion(excursion: Excursion,profile: Profile?):Flow<UIResources<SetFavoriteExcursionResponse>>
    suspend fun removeFavoriteExcursion(excursion: Excursion,profile: Profile?):Flow<UIResources<DeleteFavoriteExcursionResponse>>
    suspend fun fetchExcursionsFavorite(profile: Profile?):Flow<UIResources<List<Excursion>?>>
    suspend fun restoreFavoriteExcursion(excursion: Excursion,profile: Profile?):Flow<UIResources<RestoreFavoriteExcursionResponse>>

}
