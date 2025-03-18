package com.example.GuideExpert.presentation.ProfileScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.GuideExpert.data.SessionManager
import com.example.GuideExpert.data.remote.services.ProfileService
import com.example.GuideExpert.domain.repository.DataSourceRepository
import com.example.GuideExpert.domain.repository.ProfileRepository
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.SnackbarEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

/*
sealed interface ProfileEvent {
    object GetProfileInfo : ProfileEvent
}
 */

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    val profileFlow = profileRepository.profileFlow

    val profileStateFlow = profileRepository.profileStateFlow

    private val _effectChannel = Channel<SnackbarEffect>()
    val effectFlow: Flow<SnackbarEffect> = _effectChannel.receiveAsFlow()

    suspend fun sendEffectFlow(message: String, actionLabel: String? = null) {
        Log.d("MODEL", message)
        _effectChannel.send(SnackbarEffect.ShowSnackbar(message))
    }
}