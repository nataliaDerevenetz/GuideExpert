package com.example.core.domain.impl

import com.example.core.domain.GetFiltersSortUseCase
import com.example.core.models.Filter
import com.example.core.domain.repository.DataProviderRepository
import javax.inject.Inject


class GetFiltersSortUseCaseImpl @Inject constructor(
    private val dataProviderRepository: DataProviderRepository
): GetFiltersSortUseCase {

    override operator fun invoke(): List<com.example.core.models.Filter> {
        return dataProviderRepository.getFiltersSort()
    }
}