package com.example.GuideExpert.presentation.FavoriteScreen.FavotiteMainScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GuideExpert.domain.LoadExcursionFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ExcursionsFavoriteUiEvent {
    data object OnLoadExcursionsFavorite : ExcursionsFavoriteUiEvent
}


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val loadExcursionFavoriteUseCase: LoadExcursionFavoriteUseCase,
) : ViewModel() {

    fun handleEvent(event: ExcursionsFavoriteUiEvent) {
        viewModelScope.launch {
            when (event) {
                is ExcursionsFavoriteUiEvent.OnLoadExcursionsFavorite -> loadExcursionsFavorite()
            }
        }
    }

    private suspend fun loadExcursionsFavorite() {
        loadExcursionFavoriteUseCase().flowOn(Dispatchers.IO).collectLatest { resource ->
         /*   when(resource) {
                is UIResources.Success -> {
                    _configApp.update { resource.data }
                }
                else -> {}
            }*/
        }
    }

    init{
        handleEvent(ExcursionsFavoriteUiEvent.OnLoadExcursionsFavorite)
    }
}