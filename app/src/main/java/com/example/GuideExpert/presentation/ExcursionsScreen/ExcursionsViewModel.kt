package com.example.GuideExpert.presentation.ExcursionsScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GuideExpert.domain.GetAllExcursionsUseCase
import com.example.GuideExpert.domain.models.Excursion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

sealed interface HomeScreenUiState {
    data object Empty: HomeScreenUiState
    data class Content(val excursions: List<Excursion>): HomeScreenUiState
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


}
