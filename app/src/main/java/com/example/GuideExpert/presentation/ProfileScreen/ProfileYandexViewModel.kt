package com.example.GuideExpert.presentation.ProfileScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GuideExpert.data.SessionManager
import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.domain.GetAuthTokenByYandexUseCase
import com.example.GuideExpert.domain.models.Filters
import com.example.GuideExpert.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
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
 //   val profileRepository: ProfileRepository
) : ViewModel() {

    fun loginYandex(oauthToken : String) {
        Log.d("VIEW", "111")
        viewModelScope.launch(Dispatchers.IO) {
            getAuthTokenByYandexUseCase(oauthToken)
                .collectLatest { resource ->
                    when (resource) {
                        is UIResources.Error -> { Log.d("TAG2", "authToken Error") }
                        is UIResources.Loading -> { Log.d("TAG2", "authToken Loading") }
                        is UIResources.Success -> {
                            resource.data.authToken?.let { sessionManager.setAuthToken(it) }
                            resource.data.id?.let { sessionManager.setProfileId(it) }
                            resource.data.time?.let { sessionManager.setProfileTime(it) }
                            Log.d("VIEW", "222")
                           // profileRepository.fetchProfile()
                        }
                    }
                }
        }
        Log.d("VIEW", "333")
    }
}
