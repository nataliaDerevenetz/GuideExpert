package com.example.core.domain.repository

import com.example.core.models.Avatar
import com.example.core.models.MessageResponse
import com.example.core.models.Profile
import com.example.core.models.ProfileResources
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MultipartBody
import java.util.Date

interface ProfileRepository {
    val profileFlow: StateFlow<com.example.core.models.Profile?>
    val profileStateFlow: StateFlow<com.example.core.models.ProfileResources>
    suspend fun fetchProfile()
    suspend fun updateProfile(newProfile: com.example.core.models.Profile)
    suspend fun removeProfile()
    suspend fun updateAvatarProfile(imagePart: MultipartBody.Part): Flow<com.example.core.models.UIResources<com.example.core.models.Avatar>>
    suspend fun removeAvatarProfile():Flow<com.example.core.models.UIResources<com.example.core.models.MessageResponse>>
    suspend fun updateProfile(firstName: String, lastName: String, sex: String?, email:String, birthday: Date):Flow<com.example.core.models.UIResources<com.example.core.models.MessageResponse>>
}