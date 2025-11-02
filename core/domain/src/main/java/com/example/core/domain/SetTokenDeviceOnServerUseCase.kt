package com.example.core.domain

import com.example.core.models.SetTokenDeviceResponse
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow

interface SetTokenDeviceOnServerUseCase {
    suspend operator fun invoke(token: String) : Flow<UIResources<SetTokenDeviceResponse>>
}