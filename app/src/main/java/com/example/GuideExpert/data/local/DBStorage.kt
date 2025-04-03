package com.example.GuideExpert.data.local

import com.example.GuideExpert.domain.models.ExcursionData
import com.example.GuideExpert.domain.models.ExcursionFavorite
import com.example.GuideExpert.domain.models.Image
import com.example.GuideExpert.domain.models.Profile
import kotlinx.coroutines.flow.Flow

interface DBStorage {
    fun getExcursionData(excursionId:Int): Flow<ExcursionData?>
    fun getImagesExcursion(excursionId:Int): Flow<List<Image>>
    fun getImageExcursion(excursionId:Int): Flow<Image>
    suspend fun insertExcursionInfo(excursion: ExcursionData, images:List<Image>)
    fun getProfile(profileId:Int): Flow<Profile?>
    suspend fun insertProfile(profile: Profile)
    fun getExcursionsFavorite(): Flow<List<ExcursionFavorite>>
    suspend fun insertExcursionsFavorite(excursionsId: List<ExcursionFavorite>)
}