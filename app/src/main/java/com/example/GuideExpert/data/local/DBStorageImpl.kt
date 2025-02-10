package com.example.GuideExpert.data.local

import android.util.Log
import com.example.GuideExpert.data.mappers.toExcursion
import com.example.GuideExpert.data.local.dao.ExcursionDao
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ExcursionData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DBStorageImpl @Inject constructor(
    private val excursionDao: ExcursionDao
): DBStorage{

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