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
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthSdk
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val userInfoRepository: DataSourceRepository,
    private val sessionManager: SessionManager,
    private val excursionService: ExcursionService
) : ViewModel() {

    var count by mutableStateOf(0)
    fun increase() {
        count++
    }

    fun loginYandex(oauthToken : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = excursionService.loginYandex(oauthToken)
            user.body()?.let { Log.d("YANDEX", it.login) }
        }
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