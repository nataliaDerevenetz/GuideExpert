package com.example.core.domain.impl

import com.example.core.domain.GetSortDefaultUseCase
import com.example.core.domain.repository.DataProviderRepository
import javax.inject.Inject

class GetSortDefaultUseCaseImpl @Inject constructor(
    private val dataProviderRepository: DataProviderRepository
): GetSortDefaultUseCase {

    override operator fun invoke(): Int {
        return dataProviderRepository.getSortDefault()
    }
}