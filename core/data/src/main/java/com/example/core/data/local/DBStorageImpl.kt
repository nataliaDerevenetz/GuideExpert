package com.example.core.data.local

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.example.core.data.models.toExcursion
import com.example.core.data.models.toExcursionData
import com.example.core.data.models.toExcursionDataEntity
import com.example.core.data.models.toExcursionFavorite
import com.example.core.data.models.toExcursionsFavoriteEntity
import com.example.core.data.models.toExcursionsFavoriteWithData
import com.example.core.data.models.toImage
import com.example.core.data.models.toImageEntity
import com.example.core.data.models.toProfile
import com.example.core.data.models.toProfileWithAvatar
import com.example.core.database.dao.ExcursionDataDao
import com.example.core.database.dao.ExcursionFilterDao
import com.example.core.database.dao.ExcursionSearchDao
import com.example.core.database.dao.ExcursionsFavoriteDao
import com.example.core.database.dao.FavoriteDao
import com.example.core.database.dao.ImageDao
import com.example.core.database.dao.ProfileDao
import com.example.core.database.dao.RemoteKeyDao
import com.example.core.database.db.ExcursionsRoomDatabase
import com.example.core.database.models.ExcursionFilterWithData
import com.example.core.database.models.ExcursionSearchWithData
import com.example.core.database.models.ExcursionsFavoriteWithData
import com.example.core.database.models.RemoteKeyEntity
import com.example.core.database.models.toRemoteKey
import com.example.core.models.Excursion
import com.example.core.models.ExcursionData
import com.example.core.models.ExcursionFavorite
import com.example.core.models.Image
import com.example.core.models.Profile
import com.example.core.models.RemoteKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class DBStorageImpl @Inject constructor(
    private val excursionsRoomDatabase: ExcursionsRoomDatabase,
    private val excursionDataDao: ExcursionDataDao,
    private val imageDao: ImageDao,
    private val profileDao: ProfileDao,
    private val favoriteDao: FavoriteDao,
    private val excursionsFavoriteDao: ExcursionsFavoriteDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val excursionFilterDao: ExcursionFilterDao,
    private val excursionSearchDao: ExcursionSearchDao
): DBStorage {

    override suspend fun insertExcursionInfo(excursion: ExcursionData, images:List<Image>) {
       excursionDataDao.insertExcursionAndImages(excursion.toExcursionDataEntity(),
           images.map{it.toImageEntity()})
    }

    override fun getExcursionData(excursionId: Int): Flow<ExcursionData?> {
        return excursionDataDao.getById(excursionId).mapNotNull { it?.toExcursionData() ?: null }
    }

    override fun getImagesExcursion(excursionId: Int): Flow<List<Image>> {
        return imageDao.getByExcursionId(excursionId).map{
            images -> images.map { it.toImage() }
        }
    }

    override fun getImageExcursion(imageId: Int): Flow<Image> {
        return imageDao.getById(imageId).map{
                 it.toImage()
        }
    }

    override fun getProfile(profileId: Int): Flow<Profile?> {
        return profileDao.getById(profileId).map { it?.toProfile() }
    }

    override suspend fun insertProfile(profile: Profile) {
        profileDao.insertAll(profile.toProfileWithAvatar())
    }

    override fun getExcursionsFavorite(): Flow<List<ExcursionFavorite>> {
        return favoriteDao.getAll().map {
            favorite -> favorite.map {
                it.toExcursionFavorite()
            }
        }
    }

    override suspend fun insertAllExcursionsFavorite(excursions: List<ExcursionFavorite>) {
        favoriteDao.insertAll(excursions.map {
            it.toExcursionsFavoriteEntity()
        })
    }

    override suspend fun insertExcursionFavorite(excursion: ExcursionFavorite, excursionUpdate: Excursion) {
        excursionsRoomDatabase.withTransaction {
            favoriteDao.insert(excursion.toExcursionsFavoriteEntity())
            // excursionId timestamp
            excursionsFavoriteDao.insertExcursion(excursionUpdate.toExcursionsFavoriteWithData())
        }
    }

    override suspend fun deleteExcursionFavorite(excursion: ExcursionFavorite, excursionDelete: Excursion) {
        excursionsRoomDatabase.withTransaction {
            favoriteDao.delete(excursion.toExcursionsFavoriteEntity())
            excursionsFavoriteDao.deleteExcursion(excursionDelete.toExcursionsFavoriteEntity())
        }
    }

    override suspend fun insertExcursionsFavorite(excursions: List<ExcursionsFavoriteWithData>) {
        excursionsFavoriteDao.insertAll(excursions)
    }

    override fun getExcursionFavorite(): Flow<List<Excursion>> {
        return excursionsFavoriteDao.getAll().map{
           favorite -> favorite.map {  it.toExcursion() }
        }
    }

    override fun getExcursionFavoriteById(excursionId: Int): Flow<Excursion>{
        return excursionsFavoriteDao.getExcursionById(excursionId).map{
                 it.toExcursion()
        }
    }


    override suspend fun clearAll() {
        excursionsFavoriteDao.clearAll()
    }

    override fun getRemoteKeyById(id: String): Flow<RemoteKey?> {
       return remoteKeyDao.getById(id).map { it?.toRemoteKey() }
    }

    override suspend fun insertExcursionsFilter(
        excursions: List<ExcursionFilterWithData>,
        keyId: String,
        isClearDB: Boolean,
        nextOffset: Int
    ) {
        excursionsRoomDatabase.withTransaction {
            if (isClearDB) {
                // IF REFRESHING, CLEAR DATABASE FIRST
                excursionsRoomDatabase.excursionFilterDao().clearAll()
                excursionsRoomDatabase.remoteKeyDao().deleteById(keyId)
            }
            excursionsRoomDatabase.excursionFilterDao().insertAll(excursions)
            excursionsRoomDatabase.remoteKeyDao().insert(
                RemoteKeyEntity(
                    id = keyId,
                    nextOffset = nextOffset,
                )
            )
        }
    }

    override fun getExcursionsFilter(): PagingSource<Int, ExcursionFilterWithData> {
        return  excursionFilterDao.pagingSource()
    }

    override suspend fun insertExcursionsSearch(
        excursions: List<ExcursionSearchWithData>,
        keyId: String,
        isClearDB: Boolean,
        nextOffset: Int
    ) {
        excursionsRoomDatabase.withTransaction {
            if (isClearDB) {
                // IF REFRESHING, CLEAR DATABASE FIRST
                excursionsRoomDatabase.excursionSearchDao().clearAll()
                excursionsRoomDatabase.remoteKeyDao().deleteById(keyId)
            }
            excursionsRoomDatabase.excursionSearchDao().insertAll(excursions)
            excursionsRoomDatabase.remoteKeyDao().insert(
                RemoteKeyEntity(
                    id = keyId,
                    nextOffset = nextOffset,
                )
            )
        }
    }

    override fun getExcursionsSearch(): PagingSource<Int, ExcursionSearchWithData> {
        return  excursionSearchDao.pagingSource()
    }
}