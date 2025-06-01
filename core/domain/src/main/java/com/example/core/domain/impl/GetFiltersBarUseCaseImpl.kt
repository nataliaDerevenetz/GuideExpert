package com.example.core.domain.impl

import com.example.core.domain.GetFiltersBarUseCase
import com.example.core.models.Filter
import com.example.core.domain.repository.DataProviderRepository
import javax.inject.Inject

class GetFiltersBarUseCaseImpl @Inject constructor(
    private val dataProviderRepository: DataProviderRepository
): GetFiltersBarUseCase {

    override operator fun invoke(): List<com.example.core.models.Filter> {
        return dataProviderRepository.getFiltersBar()
    }
}