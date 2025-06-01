package com.example.core.domain

import androidx.paging.PagingData
import com.example.core.models.Excursion
import com.example.core.models.FilterQuery
import kotlinx.coroutines.flow.Flow

interface GetExcursionByQueryUseCase {
    operator fun invoke(filter: com.example.core.models.FilterQuery): Flow<PagingData<com.example.core.models.Excursion>>
}