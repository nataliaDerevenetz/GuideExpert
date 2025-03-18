package com.example.GuideExpert.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GuideExpert.data.SessionManager
import com.example.GuideExpert.data.remote.services.ProfileService
import com.example.GuideExpert.domain.models.ProfileAuthYandexData
import com.example.GuideExpert.domain.repository.DataSourceRepository
import com.example.GuideExpert.domain.repository.ProfileRepository
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.SnackbarEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProfileEvent {
    object GetProfileInfo : ProfileEvent
}

@HiltViewModel
class UserViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val userInfoRepository: DataSourceRepository,
    private val sessionManager: SessionManager,
    private val profileService: ProfileService,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    val profileId: StateFlow<Int> = sessionManager.getProfileId()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val authToken: StateFlow<String> = sessionManager.getAuthToken()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")


    val profileFlow = profileRepository.profileFlow

    val profileStateFlow = profileRepository.profileStateFlow


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
                // state LOAD!!!
               profileRepository.fetchProfile()
               // profileService.getProfile(it.id as Int)
                //flowOf(it)
                //  Log.d("TAG", "get list ::${it.query}")
                //   getExcursionByQueryUseCase(it)
                //excursionRepository.getExcursionList()
            }
            .flowOn(Dispatchers.IO)
            .collect {
                // state DATA!!!
              //  profileRepository.fetchProfile()
           // println(it) // Will print "1a 2a 2b 2c"
        }

    }

    private val _effectChannel = Channel<SnackbarEffect>()
    val effectFlow: Flow<SnackbarEffect> = _effectChannel.receiveAsFlow()

    suspend fun sendEffectFlow(message: String, actionLabel: String? = null) {
        Log.d("MODEL", message)
        _effectChannel.send(SnackbarEffect.ShowSnackbar(message))
    }

    var count by mutableStateOf(0)
    fun increase() {
        Log.d("MODEL","hashCode :: "+ this.hashCode().toString())
        count++
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