package com.example.GuideExpert.data.local

import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ExcursionData
import com.example.GuideExpert.domain.models.Image
import kotlinx.coroutines.flow.Flow

interface DBStorage {
  //  fun getExcursionInfo(excursionId:Int): Flow<ExcursionData>
    fun getExcursionData(excursionId:Int): Flow<ExcursionData?>
    fun getImagesExcursion(excursionId:Int): Flow<List<Image>>
    fun getImageExcursion(excursionId:Int): Flow<Image>
    suspend fun insertExcursionInfo(excursion: ExcursionData, images:List<Image>)
}