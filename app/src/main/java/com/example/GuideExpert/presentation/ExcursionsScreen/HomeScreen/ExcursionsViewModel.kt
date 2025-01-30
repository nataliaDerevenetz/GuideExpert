package com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.GuideExpert.data.DataProvider
import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.domain.GetAllExcursionsUseCase
import com.example.GuideExpert.domain.GetExcursionByFiltersUseCase
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.Filters
import com.example.GuideExpert.utils.TriggerStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


sealed interface ExcursionsUiEvent {
    object GetFilterExcursions : ExcursionsUiEvent
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

sealed class Event {
    class ChangeFilters: Event()
    class ShowSnackbarString(val message: String): Event()
}

@HiltViewModel
class ExcursionsViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val getAllExcursionsUseCase: GetAllExcursionsUseCase,
    val getExcursionByFiltersUseCase: GetExcursionByFiltersUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ExcursionsUIState())
    val viewState: StateFlow<ExcursionsUIState> = _viewState

    private val _effectChannel = Channel<SnackbarEffect>()
    val effectFlow: Flow<SnackbarEffect> = _effectChannel.receiveAsFlow()

    private val _sortState = MutableStateFlow(DataProvider.sortDefault)
    val sortState: StateFlow<Int> = _sortState

    private val _uiPagingState = MutableStateFlow<PagingData<Excursion>>(PagingData.empty())
    val uiPagingState: StateFlow<PagingData<Excursion>> = _uiPagingState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<Event>(replay = 1)
    val eventsFlow = _eventFlow.asSharedFlow()


    fun handleEvent(event: ExcursionsUiEvent) {
        viewModelScope.launch {
            when (event) {
                is ExcursionsUiEvent.OnLoadExcursions -> loadExcursions()
                is ExcursionsUiEvent.OnClickFavoriteExcursion -> setFavoriteExcursion(event.excursion)
                is ExcursionsUiEvent.GetFilterExcursions -> getFiltersExcursions()
            }
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


    private fun getFiltersExcursions() {
        Log.d("TAG", "getFiltersExcursions")
        viewModelScope.launch {
            eventsFlow.collectLatest { event ->
                Log.d("TAG", "collect event")
                when (event) {
                    is Event.ChangeFilters -> {
                        val filters = Filters(1, listOf(1), listOf(1), listOf(1))
                        getExcursionByFiltersUseCase(filters).cachedIn(viewModelScope).collectLatest {
                            _uiPagingState.value = it
                        }
                    }

                    is Event.ShowSnackbarString -> {}
                }
            }
        }

    }

    init {
        handleEvent(ExcursionsUiEvent.OnLoadExcursions)
        handleEvent(ExcursionsUiEvent.GetFilterExcursions)
        sendEvent(Event.ChangeFilters())
    }

    fun sendEvent(event: Event) {
        Log.d("TAG", "sendEvent")
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

}
