package com.example.GuideExpert.data.local

import com.example.GuideExpert.data.local.dao.ExcursionDataDao
import com.example.GuideExpert.data.local.dao.ImageDao
import com.example.GuideExpert.data.local.dao.ProfileDao
import com.example.GuideExpert.data.mappers.toExcursionData
import com.example.GuideExpert.data.mappers.toExcursionDataEntity
import com.example.GuideExpert.data.mappers.toExcursionsFavoriteIdEntity
import com.example.GuideExpert.data.mappers.toImage
import com.example.GuideExpert.data.mappers.toImageEntity
import com.example.GuideExpert.data.mappers.toInt
import com.example.GuideExpert.data.mappers.toProfile
import com.example.GuideExpert.data.mappers.toProfileWithAvatar
import com.example.GuideExpert.domain.models.ExcursionData
import com.example.GuideExpert.domain.models.Image
import com.example.GuideExpert.domain.models.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class DBStorageImpl @Inject constructor(
    private val excursionDataDao: ExcursionDataDao,
    private val imageDao: ImageDao,
    private val profileDao: ProfileDao
): DBStorage{

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

    override fun getExcursionsFavoriteId(): Flow<List<Int>> {
        return profileDao.getExcursionsFavoriteId().map {
            favorite -> favorite.map {
                it.toInt()
            }
        }
    }

    override suspend fun insertExcursionsFavoriteId(excursionsId: List<Int>) {
        profileDao.insertExcursionsFavoriteId(excursionsId.map {
            it.toExcursionsFavoriteIdEntity()
        })
    }
}