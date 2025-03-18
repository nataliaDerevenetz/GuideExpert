package com.example.GuideExpert.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GuideExpert.data.SessionManager
import com.example.GuideExpert.data.remote.services.ProfileService
import com.example.GuideExpert.domain.GetProfileUseCase
import com.example.GuideExpert.domain.models.ProfileAuthYandexData
import com.example.GuideExpert.domain.repository.DataSourceRepository
import com.example.GuideExpert.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProfileEvent {
    object GetProfileInfo : ProfileEvent
}

@HiltViewModel
class MainViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val sessionManager: SessionManager,
    private val getProfileUseCase: GetProfileUseCase
    ) : ViewModel() {
    val profileId: StateFlow<Int> = sessionManager.getProfileId()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val authToken: StateFlow<String> = sessionManager.getAuthToken()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    private suspend fun getProfileInfo() {
        Log.d("MODEL","getProfilemmm ")
        Log.d("MODEL0",profileId.toString())
        Log.d("MODEL0",authToken.toString())

        combine(
            profileId,
            authToken,
        ) { (profileId, authToken) ->
            Log.d("SERVER111", "1111 ::")
            Log.d("SERVER111","profileId: $profileId")
            Log.d("SERVER111", "authToken: $authToken")
            ProfileAuthYandexData(profileId, authToken)
        }.filter{it.id !=0 && it.authToken !=""}
            .distinctUntilChanged()
            .onEach {
                Log.d("SERVER", "222 ::${it.authToken}")
                getProfileUseCase()
            }
            .flowOn(Dispatchers.IO)
            .collect {}
    }


    fun handleEvent(event: ProfileEvent) {
        viewModelScope.launch {
            when (event) {
                is ProfileEvent.GetProfileInfo -> {
                    getProfileInfo()
                }
            }
        }
    }

    init {
        Log.d("MODEL","ProfileEvent :: ")
        handleEvent(ProfileEvent.GetProfileInfo)
    }
}


