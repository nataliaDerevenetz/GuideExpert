package com.example.core.domain.impl

import com.example.core.domain.GetExcursionFavoriteUseCase
import com.example.core.domain.repository.ExcursionsRepository
import javax.inject.Inject

class GetExcursionFavoriteUseCaseImpl @Inject constructor(
    private val excursionsRepository: ExcursionsRepository
): GetExcursionFavoriteUseCase {
    override operator fun invoke() = excursionsRepository.getExcursionFavoriteFlow()
}