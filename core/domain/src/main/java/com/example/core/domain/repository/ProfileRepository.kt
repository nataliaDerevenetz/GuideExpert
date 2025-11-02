package com.example.core.domain.repository

import com.example.core.models.Avatar
import com.example.core.models.MessageResponse
import com.example.core.models.Profile
import com.example.core.models.ProfileResources
import com.example.core.models.SetTokenDeviceResponse
import com.example.core.models.UIResources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MultipartBody
import java.util.Date

interface ProfileRepository {
    val profileFlow: StateFlow<Profile?>
    val profileStateFlow: StateFlow<ProfileResources>
    suspend fun fetchProfile()
    suspend fun updateProfile(newProfile: Profile)
    suspend fun removeProfile()
    suspend fun updateAvatarProfile(imagePart: MultipartBody.Part): Flow<UIResources<Avatar>>
    suspend fun removeAvatarProfile():Flow<UIResources<MessageResponse>>
    suspend fun updateProfile(firstName: String, lastName: String, sex: String?, email:String, birthday: Date):Flow<UIResources<MessageResponse>>
    suspend fun registerTokenDevice(token:String):Flow<UIResources<SetTokenDeviceResponse>>
}