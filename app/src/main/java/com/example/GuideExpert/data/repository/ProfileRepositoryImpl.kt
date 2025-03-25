package com.example.GuideExpert.data.repository

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.GuideExpert.data.SessionManager
import com.example.GuideExpert.data.local.DBStorage
import com.example.GuideExpert.data.mappers.toAvatar
import com.example.GuideExpert.data.mappers.toConfig
import com.example.GuideExpert.data.mappers.toProfile
import com.example.GuideExpert.data.remote.services.ProfileService
import com.example.GuideExpert.domain.models.Avatar
import com.example.GuideExpert.domain.models.Config
import com.example.GuideExpert.domain.models.ExcursionData
import com.example.GuideExpert.domain.models.Profile
import com.example.GuideExpert.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

sealed class ProfileResources {
    data object Success : ProfileResources()
    data class Error(val message: String) : ProfileResources()
    data object Loading : ProfileResources()
    data object Idle : ProfileResources()
}

class ProfileRepositoryImpl @Inject constructor(
    private val dbStorage : DBStorage,
    private val profileService: ProfileService,
    private val sessionManager: SessionManager,
): ProfileRepository {
    private val _profileFlow = MutableStateFlow<Profile?>(null)
    override val profileFlow: StateFlow<Profile?> get() = _profileFlow

    private val _profileStateFlow = MutableStateFlow<ProfileResources>(ProfileResources.Idle)
    override val profileStateFlow: StateFlow<ProfileResources> get() = _profileStateFlow

    override suspend fun updateAvatarProfile(imagePart: MultipartBody.Part): Flow<UIResources<Avatar>> = flow {
        try {
            emit(UIResources.Loading)
            val result = profileService.updateAvatarProfile(profileFlow.value?.id.toString().toRequestBody("text/plain".toMediaTypeOrNull()),imagePart)
            if (result.code() == 403) {
                removeProfile()
            }
            if (result.isSuccessful) {
                val newAvatar = result.body()?.toAvatar(profileFlow.value?.id!!) ?: Avatar()
                if ( newAvatar.id != null ) {
                    profileFlow.value?.let {
                        updateProfile(it.copy(avatar = newAvatar))
                    }
                    emit(UIResources.Success(newAvatar))
                } else {
                    emit(UIResources.Error("Avatar loaded error"))
                }
            }
        } catch (e:Exception) {
            Log.d("TAG", "ERROR")
            emit(UIResources.Error(e.message.toString()))
        }

    }

    override suspend fun fetchProfile() {
        try {
            Log.d("VIEW999", "1")
            val profileId = runBlocking {
                sessionManager.getProfileId().first()
            }
            if (profileId != 0) {
                Log.d("VIEW999", "2")
                _profileStateFlow.update { ProfileResources.Loading }
                val localProfile = dbStorage.getProfile(profileId).firstOrNull()
                if (localProfile !== null) {
                    _profileFlow.update { localProfile }
                    Log.d("VIEW999", "3")
                //    _profileFlow2.update { ProfileResources.Success(localProfile) }
                }
                val result = profileService.getProfile(profileId)
                Log.d("VIEW999", "4")
                if (result.code() == 403) {
                    removeProfile()
                }
                if (result.isSuccessful) {
                    val profile = result.body()?.toProfile()
                    _profileFlow.update { profile }
                    dbStorage.insertProfile(profile!!)
                    Log.d("VIEW999", "5")
                    _profileStateFlow.update { ProfileResources.Success }
                }
            }
        } catch (e:Exception) {
            Log.d("TAG", "ERROR")
            _profileStateFlow.update { ProfileResources.Error(e.message.toString()) }
        }
    }

    override suspend fun updateProfile(newProfile: Profile) {
        _profileFlow.update {newProfile}
        dbStorage.insertProfile(newProfile)
    }

    override suspend fun removeProfile() {
        sessionManager.setAuthToken("")
        sessionManager.setProfileId(0)
        sessionManager.setProfileTime(0)
        _profileFlow.update {null}
        _profileStateFlow.update { ProfileResources.Idle }
    }

}