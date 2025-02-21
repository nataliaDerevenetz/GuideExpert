package com.example.GuideExpert.data.local

import android.util.Log
import com.example.GuideExpert.data.mappers.toExcursion
import com.example.GuideExpert.data.local.dao.ExcursionDao
import com.example.GuideExpert.data.local.dao.ExcursionDataDao
import com.example.GuideExpert.data.local.models.ExcursionDataEntity
import com.example.GuideExpert.data.local.models.ImageEntity
import com.example.GuideExpert.data.mappers.toExcursionDataEntity
import com.example.GuideExpert.data.mappers.toImageEntity
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ExcursionData
import com.example.GuideExpert.domain.models.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DBStorageImpl @Inject constructor(
    private val excursionDao: ExcursionDao,
    private val excursionDataDao: ExcursionDataDao
): DBStorage{

    override fun getExcursionInfo(excursionId: Int): Flow<ExcursionData> {
        Log.d("TAG","excursionId::   ${excursionId.toString()}")
        return flow{
            /*   withContext(Dispatchers.IO) {
                   delay(5000)
               }

             */
            emit(ExcursionData(excursionId = excursionId))
        }
    }


    override suspend fun insertExcursionInfo(excursion: ExcursionData, images:List<Image>) {
       excursionDataDao.insertExcursionAndImages(excursion.toExcursionDataEntity(),
           images.map{it.toImageEntity()})
    }
}