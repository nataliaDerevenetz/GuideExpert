package com.example.core.domain.impl

import com.example.core.domain.GetExcursionDetailUseCase
import com.example.core.models.ExcursionData
import com.example.core.models.UIResources
import com.example.core.domain.repository.ExcursionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExcursionDetailUseCaseImpl @Inject constructor(
    private val excursionsRepository: ExcursionsRepository
): GetExcursionDetailUseCase {

    override suspend operator fun invoke(excursionId: Int): Flow<com.example.core.models.UIResources<com.example.core.models.ExcursionData>> {
        return excursionsRepository.getExcursionInfo(excursionId)
    }
}

