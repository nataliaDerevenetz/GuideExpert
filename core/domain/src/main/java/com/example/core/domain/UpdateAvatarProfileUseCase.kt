package com.example.core.domain

import com.example.core.models.Avatar
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface UpdateAvatarProfileUseCase {
    suspend operator fun invoke(imagePart: MultipartBody.Part) : Flow<com.example.core.models.UIResources<com.example.core.models.Avatar>>
}