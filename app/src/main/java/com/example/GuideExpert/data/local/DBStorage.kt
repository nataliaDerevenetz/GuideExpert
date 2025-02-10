package com.example.GuideExpert.data.local

import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ExcursionData
import kotlinx.coroutines.flow.Flow

interface DBStorage {
    fun getExcursionInfo(excursionId:Int): Flow<ExcursionData>
}