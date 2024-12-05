package com.example.GuideExpert.domain.repository

import com.example.GuideExpert.data.repository.UserInfo
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ExcursionData
import kotlinx.coroutines.flow.Flow

interface DataSourceRepository {
    fun getAllExcursionFlow(): Flow<List<Excursion>>

    fun getUserInfo(userId:String): Flow<UserInfo>

    fun getExcursionInfo(excursionId:Int): Flow<ExcursionData>
}