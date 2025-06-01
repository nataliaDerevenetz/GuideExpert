package com.example.core.domain.impl

import com.example.core.domain.GetImagesExcursionDataUseCase
import com.example.core.models.Image
import com.example.core.domain.repository.ExcursionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetImagesExcursionDataUseCaseImpl @Inject constructor(
    private val excursionsRepository: ExcursionsRepository
): GetImagesExcursionDataUseCase {

    override operator fun invoke(excursionId: Int): Flow<List<com.example.core.models.Image>> {
        return excursionsRepository.getImagesExcursion(excursionId)
    }
}