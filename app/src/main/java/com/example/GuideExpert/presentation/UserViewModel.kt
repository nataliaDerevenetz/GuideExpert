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
import com.example.GuideExpert.domain.repository.DataSourceRepository
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.SnackbarEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val userInfoRepository: DataSourceRepository,
    private val sessionManager: SessionManager,
    private val excursionService: ExcursionService
) : ViewModel() {

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

    fun getToken():String? {
        return sessionManager.fetchAuthToken()
    }
    init{
       getToken()?.let{
          //send server
      }
    }

}