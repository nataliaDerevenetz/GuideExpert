package com.example.GuideExpert.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GuideExpert.data.SessionManager
import com.example.GuideExpert.data.remote.services.ExcursionService
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ProfileAuthYandexData
import com.example.GuideExpert.domain.repository.DataSourceRepository
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.ExcursionListSearchUIState
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.SearchEvent
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.SnackbarEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
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
    private val excursionService: ExcursionService
) : ViewModel() {

    val profileId: SharedFlow<Int> = sessionManager.getProfileId()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), replay = 1)


    val profileTime: SharedFlow<Int> = sessionManager.getProfileTime()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), replay = 1)


    val authToken: SharedFlow<String> = sessionManager.getAuthToken()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), replay = 1)



    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun getProfileInfo() {
        Log.d("MODEL","getProfilemmm ")
        Log.d("MODEL0",profileId.toString())
        Log.d("MODEL0",profileTime.toString())
        Log.d("MODEL0",authToken.toString())

        combine(
            profileId,
            authToken,
            profileTime
        ) { (profileId, authToken,profileTime) ->
            Log.d("SERVER111", "1111 ::")
            ProfileAuthYandexData(profileId, authToken,profileTime)
        }.filter{it.id !=0 && it.authToken !="" && it.time != 0}
            .flowOn(Dispatchers.IO)
            .flatMapLatest {
                Log.d("SERVER", "222 ::${it.authToken}")
                flowOf(it)
                //  Log.d("TAG", "get list ::${it.query}")
                //   getExcursionByQueryUseCase(it)
                //excursionRepository.getExcursionList()
            }
            .collect {
            println(it) // Will print "1a 2a 2b 2c"
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