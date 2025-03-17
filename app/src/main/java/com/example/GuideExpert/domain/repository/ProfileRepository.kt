package com.example.GuideExpert.domain.repository

import com.example.GuideExpert.domain.models.Profile
import kotlinx.coroutines.flow.StateFlow

interface ProfileRepository {
    val profileFlow: StateFlow<Profile?>
    suspend fun fetchProfile()
    suspend fun updateProfile(newProfile: Profile)
}