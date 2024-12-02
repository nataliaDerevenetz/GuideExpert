package com.example.GuideExpert.data.storage

import android.util.Log
import com.example.GuideExpert.data.DataProvider
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ExcursionData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DBStorageImpl @Inject constructor(): DBStorage{
    override fun getAllExcursionFlow(): List<Excursion> {
        val excursions = DataProvider.excursionList
        return excursions
    }

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