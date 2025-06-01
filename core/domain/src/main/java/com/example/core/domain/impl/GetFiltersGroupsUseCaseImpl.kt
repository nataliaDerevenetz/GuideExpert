package com.example.core.domain.impl

import com.example.core.domain.GetFiltersGroupsUseCase
import com.example.core.models.Filter
import com.example.core.domain.repository.DataProviderRepository
import javax.inject.Inject


class GetFiltersGroupsUseCaseImpl @Inject constructor(
    private val dataProviderRepository: DataProviderRepository
): GetFiltersGroupsUseCase {
    override operator fun invoke(): List<com.example.core.models.Filter> {
        return dataProviderRepository.getFiltersGroups()
    }
}