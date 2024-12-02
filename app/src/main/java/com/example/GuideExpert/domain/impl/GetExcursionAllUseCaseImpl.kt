package com.example.GuideExpert.domain.impl

import com.example.GuideExpert.domain.GetExcursionAllUseCase
import com.example.GuideExpert.domain.repository.DataSourceRepository
import javax.inject.Inject


class GetExcursionAllUseCaseImpl @Inject constructor(
    private val dataSourceRepository: DataSourceRepository
):GetExcursionAllUseCase{
    override operator fun invoke() = dataSourceRepository.getAllExcursionFlow()
}
