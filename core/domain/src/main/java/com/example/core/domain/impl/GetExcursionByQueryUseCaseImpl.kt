package com.example.core.domain.impl

import com.example.core.domain.GetExcursionByQueryUseCase
import com.example.core.models.FilterQuery
import com.example.core.domain.repository.ExcursionsRepository
import javax.inject.Inject


class GetExcursionByQueryUseCaseImpl @Inject constructor(
    private val excursionsRepository: ExcursionsRepository
): GetExcursionByQueryUseCase {
    override operator fun invoke(filter: com.example.core.models.FilterQuery) = excursionsRepository.getExcursionByQueryFlow(filter)
}