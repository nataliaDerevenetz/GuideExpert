package com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GuideExpert.data.DataProvider
import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.domain.GetAllExcursionsUseCase
import com.example.GuideExpert.domain.models.Excursion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


sealed interface ExcursionsUiEvent {
    data class OnClickFavoriteExcursion(val excursion: Excursion) : ExcursionsUiEvent
    data object OnLoadExcursions : ExcursionsUiEvent

}

sealed interface HomeScreenUiState {
    data object Error: HomeScreenUiState
    data object Loading: HomeScreenUiState
    data object Empty: HomeScreenUiState
    data class Content(val excursions: List<Excursion>): HomeScreenUiState
}

data class ExcursionsUIState(
    val content: HomeScreenUiState = HomeScreenUiState.Empty
)
/*
sealed class SnackbarEffect {
    data class ShowSnackbar(val message: String, val actionLabel: String? = null) : SnackbarEffect()
}*/

@HiltViewModel
class ExcursionsViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val getAllExcursionsUseCase: GetAllExcursionsUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ExcursionsUIState())
    val viewState: StateFlow<ExcursionsUIState> = _viewState

    private val _effectChannel = Channel<SnackbarEffect>()
    val effectFlow: Flow<SnackbarEffect> = _effectChannel.receiveAsFlow()

    private val _sortState = MutableStateFlow(DataProvider.sortDefault)
    val sortState: StateFlow<Int> = _sortState


    init {
        handleEvent(ExcursionsUiEvent.OnLoadExcursions)
    }

    fun handleEvent(event: ExcursionsUiEvent) {
        when (event) {
            is ExcursionsUiEvent.OnLoadExcursions -> loadExcursions()
            is ExcursionsUiEvent.OnClickFavoriteExcursion -> setFavoriteExcursion(event.excursion)
        }
    }

    private fun loadExcursions() {
        viewModelScope.launch(Dispatchers.IO) {  // Perform loading on the IO thread
            getAllExcursionsUseCase().collectLatest { resource ->
                when (resource) {
                    is UIResources.Loading -> withContext(Dispatchers.Main) {
                        _viewState.update { it.copy(content = HomeScreenUiState.Loading)  }
                    }
                    is UIResources.Success -> withContext(Dispatchers.Main) {
                        _viewState.update {
                            it.copy(content = HomeScreenUiState.Content(resource.data) )
                        }
                    }
                    is UIResources.Error -> withContext(Dispatchers.Main) {

                        Log.d("TAG","Error loading excursions: ${resource.message}")
                        _viewState.update {  it.copy(content = HomeScreenUiState.Error) }
                        _effectChannel.send(SnackbarEffect.ShowSnackbar("Error loading excursions: ${resource.message}"))
                    }
                }
            }
        }
    }


    fun setSortState(sortState:Int) {
        _sortState.update { sortState }
    }

    private fun setFavoriteExcursion(excursion: Excursion) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("CLICK","setFavoriteExcursionUseCase")
           // setFavoriteExcursionUseCase(note)

        }
    }


}
