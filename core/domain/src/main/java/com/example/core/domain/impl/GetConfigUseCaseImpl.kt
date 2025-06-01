package com.example.core.domain.impl

import com.example.core.domain.GetConfigUseCase
import com.example.core.models.Config
import com.example.core.models.UIResources
import com.example.core.domain.repository.DataSourceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConfigUseCaseImpl @Inject constructor(
    private val dataSourceRepository: DataSourceRepository
): GetConfigUseCase {
    override suspend operator fun invoke(): Flow<com.example.core.models.UIResources<com.example.core.models.Config>> {
        return dataSourceRepository.getConfigInfo()
    }
}
