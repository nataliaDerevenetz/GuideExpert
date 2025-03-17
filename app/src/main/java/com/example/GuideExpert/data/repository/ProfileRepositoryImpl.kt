package com.example.GuideExpert.data.repository

import android.util.Log
import com.example.GuideExpert.data.SessionManager
import com.example.GuideExpert.data.local.DBStorage
import com.example.GuideExpert.data.mappers.toConfig
import com.example.GuideExpert.data.mappers.toProfile
import com.example.GuideExpert.data.remote.services.ExcursionService
import com.example.GuideExpert.data.remote.services.ProfileService
import com.example.GuideExpert.domain.models.Config
import com.example.GuideExpert.domain.models.Profile
import com.example.GuideExpert.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds


class ProfileRepositoryImpl @Inject constructor(
    private val dbStorage : DBStorage,
    private val profileService: ProfileService,
    private val sessionManager: SessionManager,
): ProfileRepository {
    private val _profileFlow = MutableStateFlow<Profile?>(null)
    override val profileFlow: StateFlow<Profile?> get() = _profileFlow
    override suspend fun fetchProfile() {
        try {
            val profileId = runBlocking {
                sessionManager.getProfileId().first()
            }
            if (profileId != 0) {
                val localProfile = dbStorage.getProfile(profileId).firstOrNull()
                if (localProfile !== null) {
                    _profileFlow.update { localProfile }
                }
                val result = profileService.getProfile(profileId)
                if (result.isSuccessful) {
                    val profile = result.body()?.toProfile()
                    _profileFlow.update { profile }
                    dbStorage.insertProfile(profile!!)
                }
            }
        } catch (e:Exception) {
            Log.d("TAG", "ERROR")
        }
    }

    override suspend fun updateProfile(newProfile: Profile) {
        _profileFlow.update {newProfile}
        dbStorage.insertProfile(newProfile)
    }


}