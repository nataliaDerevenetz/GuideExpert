package com.example.GuideExpert.domain.repository

import androidx.paging.PagingData
import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.data.repository.UserInfo
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ExcursionData
import com.example.GuideExpert.domain.models.FilterQuery
import kotlinx.coroutines.flow.Flow

interface DataSourceRepository {
    fun getAllExcursionFlow(): Flow<UIResources<List<Excursion>>>

    fun getUserInfo(userId:String): Flow<UserInfo>

    fun getExcursionInfo(excursionId:Int): Flow<ExcursionData>
}