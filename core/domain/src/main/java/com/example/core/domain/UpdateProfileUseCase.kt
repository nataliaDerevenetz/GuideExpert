package com.example.core.domain

import com.example.core.models.MessageResponse
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface UpdateProfileUseCase {
    suspend operator fun invoke(firstName: String, lastName: String, sex: String?, email:String, birthday: Date) : Flow<com.example.core.models.UIResources<com.example.core.models.MessageResponse>>
}