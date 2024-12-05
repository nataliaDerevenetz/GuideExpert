package com.example.GuideExpert.data.storage

import android.util.Log
import com.example.GuideExpert.data.DataProvider
import com.example.GuideExpert.data.mappers.toExcursion
import com.example.GuideExpert.data.mappers.toExcursionEntity
import com.example.GuideExpert.data.storage.dao.ExcursionDao
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ExcursionData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DBStorageImpl @Inject constructor(
    private val excursionDao: ExcursionDao
): DBStorage{
    override fun getAllExcursionFlow(): Flow<List<Excursion>> {
     /*   val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            excursionDao.insertAllExcursionTest(DataProvider.excursionList.map { it -> it.toExcursionEntity() })

        }*/

        return excursionDao.getAllExcursion().map { excursionEntityList ->
            excursionEntityList.map { excursionEntity ->
                excursionEntity.toExcursion()
            }
        }


    //    val excursions = DataProvider.excursionList
    //    return excursions
    }

    //

    override fun getExcursionInfo(excursionId: Int): Flow<ExcursionData> {
        Log.d("TAG","excursionId::   ${excursionId.toString()}")
        return flow{
            /*   withContext(Dispatchers.IO) {
                   delay(5000)
               }

             */
            emit(ExcursionData(excursionId = excursionId.toString()))
        }
    }
}