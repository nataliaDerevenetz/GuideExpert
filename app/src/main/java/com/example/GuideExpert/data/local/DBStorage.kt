package com.example.GuideExpert.data.local

import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ExcursionData
import com.example.GuideExpert.domain.models.Image
import kotlinx.coroutines.flow.Flow

interface DBStorage {
    fun getExcursionInfo(excursionId:Int): Flow<ExcursionData>

    suspend fun insertExcursionInfo(excursion: ExcursionData, images:List<Image>)
}