package com.example.GuideExpert.presentation.ProfileScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.domain.GetAuthTokenByYandexUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileYandexViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val getAuthTokenByYandexUseCase: GetAuthTokenByYandexUseCase
) : ViewModel() {
     fun loginYandex(oauthToken : String) {
        viewModelScope.launch {
            getAuthTokenByYandexUseCase(oauthToken).flowOn(Dispatchers.IO)
                .collectLatest { resource ->
                    when (resource) {
                        is UIResources.Error -> { Log.d("TAG2", "authToken Error") }
                        is UIResources.Loading -> { Log.d("TAG2", "authToken Loading") }
                        is UIResources.Success -> {
                            resource.data.authToken?.let { Log.d("YANDEX", it) }
                        }
                    }
                }
        }
    }
}
