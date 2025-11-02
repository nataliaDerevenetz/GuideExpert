package com.example.core.domain.impl

import android.util.Log
import com.example.core.domain.SetTokenDeviceOnServerUseCase
import com.example.core.domain.repository.ProfileRepository
import com.example.core.models.SetTokenDeviceResponse
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetTokenDeviceOnServerUseCaseImpl  @Inject constructor(
    private val profileRepository: ProfileRepository
): SetTokenDeviceOnServerUseCase {
    override suspend operator fun invoke(token: String):  Flow<UIResources<SetTokenDeviceResponse>>{
        return profileRepository.registerTokenDevice(token)
    }
}