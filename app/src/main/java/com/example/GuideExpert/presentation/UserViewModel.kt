package com.example.GuideExpert.presentation

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GuideExpert.data.SessionManager
import com.example.GuideExpert.data.remote.services.ExcursionService
import com.example.GuideExpert.domain.repository.DataSourceRepository
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.SnackbarEffect
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthSdk
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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

    init{
        Log.d("MODEL","hashCode :: "+ this.hashCode().toString())

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

    fun loginYandex(oauthToken : String) {
        Log.d("MODEL","hashCode2 :: "+ this.hashCode().toString())
        viewModelScope.launch(Dispatchers.IO) {
            val user = excursionService.loginYandex(oauthToken)
            user.body()?.let {
                Log.d("YANDEX", it.id.toString()) }
        }
    }

    fun getToken():String? {
        return sessionManager.fetchAuthToken()
    }
    init{
        Log.d("MODEL","5555")
        Log.d("MODEL","hashCode :: "+ this.hashCode().toString())
      getToken()?.let{
          //send server
      }
    }

}