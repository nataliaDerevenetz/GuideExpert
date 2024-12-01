package com.example.GuideExpert.domain.impl

import com.example.GuideExpert.DataSourceRepository
import com.example.GuideExpert.ExcursionData
import com.example.GuideExpert.domain.GetExcursionDetailUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExcursionDetailUseCaseImpl @Inject constructor(
    private val dataSourceRepository: DataSourceRepository
): GetExcursionDetailUseCase {

    override operator fun invoke(excursionId: Int): Flow<ExcursionData> {
        return dataSourceRepository.getExcursionInfo(excursionId)
    }
}

