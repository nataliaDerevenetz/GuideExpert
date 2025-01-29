package com.example.GuideExpert.domain.repository

import androidx.paging.PagingData
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.FilterQuery
import kotlinx.coroutines.flow.Flow

interface DataPagingRepository {
    fun getExcursionByQueryFlow(filterQuery: FilterQuery): Flow<PagingData<Excursion>>
}