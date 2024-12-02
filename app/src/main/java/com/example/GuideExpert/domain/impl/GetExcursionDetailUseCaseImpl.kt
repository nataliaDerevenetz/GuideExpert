package com.example.GuideExpert.domain.impl

import com.example.GuideExpert.data.repository.DataSourceRepository
//import com.example.GuideExpert.ExcursionData
import com.example.GuideExpert.domain.GetExcursionDetailUseCase
import com.example.GuideExpert.domain.models.ExcursionData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExcursionDetailUseCaseImpl @Inject constructor(
    private val dataSourceRepository: DataSourceRepository
): GetExcursionDetailUseCase {

    override operator fun invoke(excursionId: Int): Flow<ExcursionData> {
        return dataSourceRepository.getExcursionInfo(excursionId)
    }
}

