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
import com.example.GuideExpert.domain.models.Filter
import com.example.GuideExpert.domain.models.Filters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


sealed interface ExcursionsUiEvent {
    data class OnClickFavoriteExcursion(val excursion: Excursion) : ExcursionsUiEvent
    data object OnLoadExcursions : ExcursionsUiEvent
    object ChangeFilters : ExcursionsUiEvent

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

    private var oldFilters:Filters = Filters(DataProvider.sortDefault, listOf(), listOf(), listOf())
    private val defaultFilters:Filters = Filters(DataProvider.sortDefault, listOf(), listOf(), listOf())


    fun handleEvent(event: ExcursionsUiEvent) {
        viewModelScope.launch {
            when (event) {
                is ExcursionsUiEvent.OnLoadExcursions -> loadExcursions()
                is ExcursionsUiEvent.OnClickFavoriteExcursion -> setFavoriteExcursion(event.excursion)
                is ExcursionsUiEvent.ChangeFilters -> loadExcursionsFilters()
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

    fun resetFilters() {
        _sortState.value = DataProvider.sortDefault
        val filtersBar = DataProvider.filtersBar
        filtersBar.map { it.enabled.value = false }

        val filtersCategories = DataProvider.filtersCategories
        filtersCategories.map { it.enabled.value = false }

        val filtersGroups = DataProvider.filtersGroups
        filtersGroups.map { it.enabled.value = false }

        val filtersDuration = DataProvider.filtersDuration
        filtersDuration.map { it.enabled.value = false }

        val filtersSort = DataProvider.filtersSort
        filtersSort.map { it.enabled.value = false }
    }

    fun isChangedFilters(): Boolean {
        if (oldFilters.sort != sortState.value) return true
        if (oldFilters.categories != DataProvider.filtersCategories.filter { it.enabled.value}.map{it.id}) return true
        if (oldFilters.duration != DataProvider.filtersDuration.filter { it.enabled.value}.map{it.id}) return true
        if (oldFilters.group != DataProvider.filtersGroups.filter { it.enabled.value}.map{it.id}) return true
        return false
    }

    fun isChangedDefaultFilters(): Boolean {
        if (defaultFilters.sort != sortState.value) return true
        if (defaultFilters.categories != DataProvider.filtersCategories.filter { it.enabled.value}.map{it.id}) return true
        if (defaultFilters.duration != DataProvider.filtersDuration.filter { it.enabled.value}.map{it.id}) return true
        if (defaultFilters.group != DataProvider.filtersGroups.filter { it.enabled.value}.map{it.id}) return true
        return false
    }


    fun setOldFilters(filters:Filters) {
        oldFilters = filters
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

    private fun loadExcursionsFilters() {
        viewModelScope.launch {
            val filters = Filters(sortState.value,
                DataProvider.filtersCategories.filter { it.enabled.value}.map{it.id},
                DataProvider.filtersDuration.filter { it.enabled.value}.map{it.id},
                DataProvider.filtersGroups.filter { it.enabled.value}.map{it.id})
            getExcursionByFiltersUseCase(filters).cachedIn(viewModelScope).collectLatest {
                _uiPagingState.value = it
            }
        }
    }

    init {
        handleEvent(ExcursionsUiEvent.OnLoadExcursions)
        handleEvent(ExcursionsUiEvent.ChangeFilters)
    }


}
