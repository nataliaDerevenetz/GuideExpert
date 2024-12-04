package com.example.GuideExpert.domain.impl

import com.example.GuideExpert.domain.GetAllExcursionsUseCase
import com.example.GuideExpert.domain.repository.DataSourceRepository
import javax.inject.Inject


class GetAllExcursionsUseCaseImpl @Inject constructor(
    private val dataSourceRepository: DataSourceRepository
):GetAllExcursionsUseCase{
    override operator fun invoke() = dataSourceRepository.getAllExcursionFlow()
}
