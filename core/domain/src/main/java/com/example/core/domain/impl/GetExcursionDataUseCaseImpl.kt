package com.example.core.domain.impl

import com.example.core.domain.GetExcursionDataUseCase
import com.example.core.models.ExcursionData
import com.example.core.domain.repository.ExcursionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetExcursionDataUseCaseImpl @Inject constructor(
    private val excursionsRepository: ExcursionsRepository
): GetExcursionDataUseCase {

    override operator fun invoke(excursionId: Int): Flow<com.example.core.models.ExcursionData?> {
        return excursionsRepository.getExcursionData(excursionId)
    }
}
