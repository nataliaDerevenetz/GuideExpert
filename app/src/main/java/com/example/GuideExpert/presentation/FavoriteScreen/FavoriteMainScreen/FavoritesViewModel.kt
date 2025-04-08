package com.example.GuideExpert.presentation.FavoriteScreen.FavoriteMainScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.domain.GetExcursionFavoriteUseCase
import com.example.GuideExpert.domain.LoadExcursionFavoriteUseCase
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.SnackbarEffect
import com.example.GuideExpert.presentation.ProfileScreen.EditorProfileScreen.UpdateProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed interface LoadFavoritesState{
    object Idle:LoadFavoritesState
    object Loading:LoadFavoritesState
    object Success:LoadFavoritesState
    data class Error(val error: String):LoadFavoritesState
}

data class LoadFavoritesUIState(
    val contentState: LoadFavoritesState = LoadFavoritesState.Idle
)

sealed interface ExcursionsFavoriteUiEvent {
    data object OnLoadExcursionsFavorite : ExcursionsFavoriteUiEvent
    data object OnLoadFavoritesUIStateSetIdle : ExcursionsFavoriteUiEvent
}


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val loadExcursionFavoriteUseCase: LoadExcursionFavoriteUseCase,
    val getExcursionFavoriteUseCase: GetExcursionFavoriteUseCase
) : ViewModel() {

    val excursions: Flow<List<Excursion>> = getExcursionFavoriteUseCase()

    private val _stateLoadFavorites = MutableStateFlow<LoadFavoritesUIState>(LoadFavoritesUIState())
    val stateLoadFavorites: StateFlow<LoadFavoritesUIState> = _stateLoadFavorites.asStateFlow()

    private val _effectChannel = Channel<SnackbarEffect>()
    val effectFlow: Flow<SnackbarEffect> = _effectChannel.receiveAsFlow()


    fun handleEvent(event: ExcursionsFavoriteUiEvent) {
        viewModelScope.launch {
            when (event) {
                is ExcursionsFavoriteUiEvent.OnLoadExcursionsFavorite -> loadExcursionsFavorite()
                is ExcursionsFavoriteUiEvent.OnLoadFavoritesUIStateSetIdle -> setIdleLoadFavoritesUIState()
            }
        }
    }

    suspend fun sendEffectFlow(message: String, actionLabel: String? = null) {
        _effectChannel.send(SnackbarEffect.ShowSnackbar(message))
    }

    private fun setIdleLoadFavoritesUIState() {
        _stateLoadFavorites.update { it.copy(contentState = LoadFavoritesState.Idle) }
    }

    private suspend fun loadExcursionsFavorite() {
        loadExcursionFavoriteUseCase().flowOn(Dispatchers.IO).collectLatest { resources ->
            when(resources) {
                is UIResources.Success -> withContext(Dispatchers.Main){
                    Log.d("FAVORITES","111")
                    _stateLoadFavorites.update { it.copy(contentState = LoadFavoritesState.Success) }
                }
                is UIResources.Error -> withContext(Dispatchers.Main){
                    _stateLoadFavorites.update {
                        it.copy(
                            contentState = LoadFavoritesState.Error(
                                resources.message
                            )
                        )
                    }
                    sendEffectFlow("Error loading favorites : ${resources.message}")
                }
                is UIResources.Loading -> withContext(Dispatchers.Main){
                    _stateLoadFavorites.update { it.copy(contentState = LoadFavoritesState.Loading) }
                }
            }
        }
    }

    init{
        handleEvent(ExcursionsFavoriteUiEvent.OnLoadExcursionsFavorite)
    }
}