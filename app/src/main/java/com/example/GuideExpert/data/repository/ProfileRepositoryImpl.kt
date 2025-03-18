package com.example.GuideExpert.data.repository

import android.util.Log
import com.example.GuideExpert.data.SessionManager
import com.example.GuideExpert.data.local.DBStorage
import com.example.GuideExpert.data.mappers.toProfile
import com.example.GuideExpert.data.remote.services.ProfileService
import com.example.GuideExpert.domain.models.Profile
import com.example.GuideExpert.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
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


}