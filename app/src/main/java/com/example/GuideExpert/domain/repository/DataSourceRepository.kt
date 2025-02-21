package com.example.GuideExpert.domain.repository

import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.data.repository.UserInfo
import com.example.GuideExpert.domain.models.Config
import com.example.GuideExpert.domain.models.ExcursionData
import kotlinx.coroutines.flow.Flow

interface DataSourceRepository {

    fun getUserInfo(userId:String): Flow<UserInfo>

    suspend fun getExcursionInfo(excursionId:Int): Flow<UIResources<ExcursionData>>

    suspend fun getConfigInfo(): Flow<UIResources<Config>>

    fun getExcursionData(excursionId:Int): Flow<ExcursionData?>
}