package com.example.GuideExpert.domain.repository

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import com.example.GuideExpert.data.repository.ProfileResources
import com.example.GuideExpert.domain.models.Profile
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MultipartBody

interface ProfileRepository {
    val profileFlow: StateFlow<Profile?>
    val profileStateFlow: StateFlow<ProfileResources>
    suspend fun fetchProfile()
    suspend fun updateProfile(newProfile: Profile)
    suspend fun removeProfile()
    suspend fun saveAvatarProfile(imagePart: MultipartBody.Part)
}