package com.example.core.domain.impl

import com.example.core.domain.GetExcursionByFiltersUseCase
import com.example.core.models.Filters
import com.example.core.domain.repository.ExcursionsRepository
import javax.inject.Inject

class GetExcursionByFiltersUseCaseImpl @Inject constructor(
    private val excursionsRepository: ExcursionsRepository
): GetExcursionByFiltersUseCase {
    override operator fun invoke(filter: com.example.core.models.Filters) = excursionsRepository.getExcursionByFiltersFlow(filter)
}