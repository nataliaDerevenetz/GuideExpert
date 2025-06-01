package com.example.core.domain

import androidx.paging.PagingData
import com.example.core.models.Excursion
import com.example.core.models.Filters
import kotlinx.coroutines.flow.Flow

interface GetExcursionByFiltersUseCase {
    operator fun invoke(filter: com.example.core.models.Filters): Flow<PagingData<com.example.core.models.Excursion>>
}