package com.example.core.domain.impl

import com.example.core.domain.GetFiltersCategoriesUseCase
import com.example.core.models.Filter
import com.example.core.domain.repository.DataProviderRepository
import javax.inject.Inject

class GetFiltersCategoriesUseCaseImpl @Inject constructor(
    private val dataProviderRepository: DataProviderRepository
): GetFiltersCategoriesUseCase {
    override operator fun invoke(): List<com.example.core.models.Filter> {
        return dataProviderRepository.getFiltersCategories()
    }
}