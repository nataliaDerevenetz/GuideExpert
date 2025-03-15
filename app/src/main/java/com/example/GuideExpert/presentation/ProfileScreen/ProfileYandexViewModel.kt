package com.example.GuideExpert.presentation.ProfileScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GuideExpert.data.SessionManager
import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.domain.GetAuthTokenByYandexUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileYandexViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val getAuthTokenByYandexUseCase: GetAuthTokenByYandexUseCase,
    val sessionManager: SessionManager
) : ViewModel() {
     fun loginYandex(oauthToken : String) {
        viewModelScope.launch(Dispatchers.IO) {
            getAuthTokenByYandexUseCase(oauthToken)
                .collectLatest { resource ->
                    when (resource) {
                        is UIResources.Error -> { Log.d("TAG2", "authToken Error") }
                        is UIResources.Loading -> { Log.d("TAG2", "authToken Loading") }
                        is UIResources.Success -> {
                            resource.data.authToken?.let {
                                sessionManager.setAuthToken(it)
                                Log.d("YANDEX", "AUTH :: "+ it) }
                            resource.data.id?.let {
                                sessionManager.setProfileId(it)
                                Log.d("YANDEX", "ID :: " + it.toString()) }
                            resource.data.time?.let {
                                sessionManager.setProfileTime(it)
                                Log.d("YANDEX", "ID :: " + it.toString()) }
                        }
                    }
                }
        }
    }
}
