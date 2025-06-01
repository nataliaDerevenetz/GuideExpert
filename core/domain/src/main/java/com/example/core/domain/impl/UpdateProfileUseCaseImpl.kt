package com.example.core.domain.impl

import com.example.core.domain.UpdateProfileUseCase
import com.example.core.models.MessageResponse
import com.example.core.models.UIResources
import com.example.core.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class UpdateProfileUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository
): UpdateProfileUseCase {
    override suspend operator fun invoke(firstName: String, lastName: String, sex: String?, email:String, birthday: Date): Flow<com.example.core.models.UIResources<com.example.core.models.MessageResponse>> {
        return profileRepository.updateProfile(firstName,lastName,sex,email,birthday)
    }
}