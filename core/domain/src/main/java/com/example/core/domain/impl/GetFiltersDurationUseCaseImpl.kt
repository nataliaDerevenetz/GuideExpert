package com.example.core.domain.impl

import com.example.core.domain.GetFiltersDurationUseCase
import com.example.core.models.Filter
import com.example.core.domain.repository.DataProviderRepository
import javax.inject.Inject

class GetFiltersDurationUseCaseImpl @Inject constructor(
    private val dataProviderRepository: DataProviderRepository
): GetFiltersDurationUseCase {

    override operator fun invoke(): List<com.example.core.models.Filter> {
        return dataProviderRepository.getFiltersDuration()
    }
}