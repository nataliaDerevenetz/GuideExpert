package com.example.core.domain.impl

import com.example.core.domain.GetImageExcursionUseCase
import com.example.core.models.Image
import com.example.core.domain.repository.ExcursionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetImageExcursionUseCaseImpl @Inject constructor(
    private val excursionsRepository: ExcursionsRepository
): GetImageExcursionUseCase {
    override operator fun invoke(imageId: Int): Flow<com.example.core.models.Image> {
        return excursionsRepository.getImageExcursion(imageId)
    }
}