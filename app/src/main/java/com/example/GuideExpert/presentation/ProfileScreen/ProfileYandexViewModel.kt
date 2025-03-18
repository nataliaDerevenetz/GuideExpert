package com.example.GuideExpert.presentation.ProfileScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GuideExpert.data.SessionManager
import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.domain.GetAuthTokenByYandexUseCase
import com.example.GuideExpert.domain.models.Filters
import com.example.GuideExpert.domain.repository.DataSourceRepository
import com.example.GuideExpert.domain.repository.ProfileRepository
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.ExcursionsSearchUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class ProfileYandexViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val getAuthTokenByYandexUseCase: GetAuthTokenByYandexUseCase,
    val sessionManager: SessionManager,
) : ViewModel() {

    private val _isClosed = MutableStateFlow(false)
    val isClosed: StateFlow<Boolean> = _isClosed.asStateFlow()

    fun loginYandex(oauthToken : String) {
        Log.d("VIEW", "111")
        viewModelScope.launch {
            getAuthTokenByYandexUseCase(oauthToken).flowOn(Dispatchers.IO)
                .collectLatest { resource ->
                    when (resource) {
                        is UIResources.Error -> { _isClosed.update { true }
                            Log.d("TAG2", "authToken Error") }
                        is UIResources.Loading -> { Log.d("TAG2", "authToken Loading") }
                        is UIResources.Success -> {
                            resource.data.authToken?.let { sessionManager.setAuthToken(it) }
                            resource.data.id?.let { sessionManager.setProfileId(it) }
                            resource.data.time?.let { sessionManager.setProfileTime(it) }
                            Log.d("VIEW", "222")
                           // profileRepository.fetchProfile()
                            _isClosed.update { true }
                        }
                    }
                }
        }
        Log.d("VIEW", "333")
    }
}
