package com.example.GuideExpert.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.Snapshot
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.core.domain.GetExcursionsFavoriteIdUseCase
import com.example.core.domain.GetProfileUseCase
import com.example.core.domain.SetTokenDeviceOnServerUseCase
import com.example.core.domain.repository.ProfileRepository
import com.example.core.domain.repository.SessionManager
import com.example.core.models.ProfileAuthYandexData
import com.example.core.models.RegisterTokenOnServerData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MainEvent {
    object GetProfileInfo : MainEvent
    object GetExcursionsFavoriteId: MainEvent
}

@HiltViewModel
class MainViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    sessionManager: SessionManager,
    profileRepository: ProfileRepository,
    private val getProfileUseCase: GetProfileUseCase,
    private val getExcursionsFavoriteIdUseCase: GetExcursionsFavoriteIdUseCase,
    private val setTokenDeviceOnServerUseCase: SetTokenDeviceOnServerUseCase
    ) : ViewModel() {
    val profileId: StateFlow<String> = sessionManager.getProfileId().flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val authToken: StateFlow<String> = sessionManager.getAuthToken().flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val deviceToken: StateFlow<String> = sessionManager.getDeviceToken().flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val profileFlow = profileRepository.profileFlow


    @OptIn(SavedStateHandleSaveableApi::class)
    var isOpenFromNotification: Boolean by savedStateHandle.saveable {
        mutableStateOf(false)
    }

    fun onNotificationOpened() {
        Snapshot.withMutableSnapshot {
            isOpenFromNotification = true
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    suspend fun registerTokenOnServer(token:String){
        combine(
            profileId,
            authToken,
            deviceToken
        ) { (_profileId, _authToken,_deviceToken) ->
            val id = if (_profileId.isNotEmpty()) _profileId.toInt() else 0
            RegisterTokenOnServerData(id, _authToken, _deviceToken)
        }
            .debounce(3000L)
            .filter{it.id !=0 && it.authToken !="" && it.deviceToken !=token }
            .distinctUntilChanged()
            .flatMapLatest {
                setTokenDeviceOnServerUseCase(token)
            }
            .flowOn(Dispatchers.IO)
            .collect {}
    }

    private suspend fun getProfileInfo() {
        combine(
            profileId,
            authToken,
        ) { (_profileId, _authToken) ->
            val id = if (_profileId.isNotEmpty()) _profileId.toInt() else 0
            ProfileAuthYandexData(id, _authToken)
        }.filter{it.id !=0 && it.authToken !=""}
            .distinctUntilChanged()
            .onEach {
                getProfileUseCase()
                getExcursionsFavoriteIdUseCase()
            }
            .flowOn(Dispatchers.IO)
            .collect {}
    }


    private fun handleEvent(event: MainEvent) {
        viewModelScope.launch {
            when (event) {
                is MainEvent.GetProfileInfo -> {
                    getProfileInfo()
                }

                is MainEvent.GetExcursionsFavoriteId -> {
                    getIdExcursionsFavorite()
                }
            }
        }
    }

    private suspend fun getIdExcursionsFavorite() {
        profileFlow.filter { it != null && it.id !=0 }
            .distinctUntilChanged()
            .onEach {
                getExcursionsFavoriteIdUseCase()
            }.flowOn(Dispatchers.IO).collectLatest {  }
    }

    init {
        handleEvent(MainEvent.GetProfileInfo)
    }
}


