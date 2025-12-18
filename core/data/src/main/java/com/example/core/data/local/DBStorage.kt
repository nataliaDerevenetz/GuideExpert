package com.example.core.data.local

import androidx.paging.PagingSource
import com.example.core.database.models.ExcursionFilterWithData
import com.example.core.database.models.ExcursionSearchWithData
import com.example.core.database.models.ExcursionsFavoriteWithData
import com.example.core.models.Excursion
import com.example.core.models.ExcursionData
import com.example.core.models.ExcursionFavorite
import com.example.core.models.Image
import com.example.core.models.Profile
import com.example.core.models.RemoteKey
import kotlinx.coroutines.flow.Flow

interface DBStorage {
    fun getExcursionData(excursionId:Int): Flow<ExcursionData?>
    fun getImagesExcursion(excursionId:Int): Flow<List<Image>>
    fun getImageExcursion(imageId:Int): Flow<Image>
    suspend fun insertExcursionInfo(excursion: ExcursionData, images:List<Image>)
    fun getProfile(profileId:Int): Flow<Profile?>
    suspend fun insertProfile(profile: Profile)
    fun getExcursionsFavorite(): Flow<List<ExcursionFavorite>>
    suspend fun insertAllExcursionsFavorite(excursions: List<ExcursionFavorite>)
    suspend fun insertExcursionFavorite(excursion: ExcursionFavorite, excursionUpdate: Excursion)
    suspend fun deleteExcursionFavorite(excursion: ExcursionFavorite, excursionDelete: Excursion)
    suspend fun insertExcursionsFavorite(excursions: List<ExcursionsFavoriteWithData>)
    fun getExcursionFavorite(): Flow<List<Excursion>>
    fun getExcursionFavoriteById(excursionId: Int): Flow<Excursion>
    suspend fun clearAll()

    fun getRemoteKeyById(id:String): Flow<RemoteKey?>
    suspend fun insertExcursionsFilter(excursions: List<ExcursionFilterWithData>, keyId: String, isClearDB:Boolean, nextOffset:Int)
    fun getExcursionsFilter(): PagingSource<Int, ExcursionFilterWithData>

    suspend fun insertExcursionsSearch(excursions: List<ExcursionSearchWithData>, keyId: String, isClearDB:Boolean, nextOffset:Int)
    fun getExcursionsSearch(): PagingSource<Int, ExcursionSearchWithData>

}