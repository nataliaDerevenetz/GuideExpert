package com.example.GuideExpert.domain.impl

import com.example.GuideExpert.domain.GetAllExcursionsUseCase
import com.example.GuideExpert.domain.GetExcursionByQueryUseCase
import com.example.GuideExpert.domain.models.FilterQuery
import com.example.GuideExpert.domain.repository.DataSourceRepository
import javax.inject.Inject


class GetExcursionByQueryUseCaseImpl @Inject constructor(
    private val dataSourceRepository: DataSourceRepository
): GetExcursionByQueryUseCase {
    override operator fun invoke(filter: FilterQuery) = dataSourceRepository.getGetExcursionByQueryFlow(filter)
}