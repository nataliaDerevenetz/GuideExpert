package com.example.GuideExpert.data.storage

import com.example.GuideExpert.data.repository.UserInfo
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ExcursionData
import kotlinx.coroutines.flow.Flow

interface DBStorage {
    fun getAllExcursionFlow(): List<Excursion>

    fun getExcursionInfo(excursionId:Int): Flow<ExcursionData>
}