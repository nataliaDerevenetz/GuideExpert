package com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GuideExpert.domain.GetAllExcursionsUseCase
import com.example.GuideExpert.domain.models.Excursion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeScreenUiState {
    data object Empty: HomeScreenUiState
    data class Content(val excursions: List<Excursion>): HomeScreenUiState
}

sealed interface HomeScreenUiEvent {
    data class OnFavoriteExcursionClick(val excursion: Excursion) : HomeScreenUiEvent
}


@HiltViewModel
class ExcursionsViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val getAllExcursionsUseCase: GetAllExcursionsUseCase
) : ViewModel() {
/*
    private val _allExcursion = MutableStateFlow(emptyList<Excursion>())
    val allExcursionState: StateFlow<List<Excursion>> = _allExcursion.asStateFlow()

    init {
        _allExcursion.update { getAllExcursionsUseCase() }
    }

    //val allExcursion = getExcursionAllUseCase()
*/
    fun handleEvent(event: HomeScreenUiEvent) {
        when (event) {
            is HomeScreenUiEvent.OnFavoriteExcursionClick -> setFavoriteExcursion(event.excursion)
        }
    }

    private val _allExcursion = getAllExcursionsUseCase()

    val uiState: StateFlow<HomeScreenUiState> = _allExcursion.map { excursionsList ->
        if (excursionsList.isNotEmpty())
            HomeScreenUiState.Content(excursionsList)
        else HomeScreenUiState.Empty
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeScreenUiState.Empty
    )


    private fun setFavoriteExcursion(excursion: Excursion) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("CLICK","setFavoriteExcursionUseCase")
           // setFavoriteExcursionUseCase(note)
        }
    }


}
