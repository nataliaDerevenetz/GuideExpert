package com.example.feature.home.HomeScreen.components

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.core.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileBoxViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val profileRepository: ProfileRepository,
    ) : ViewModel() {
        val profileFlow = profileRepository.profileFlow
        val profileStateFlow = profileRepository.profileStateFlow
}