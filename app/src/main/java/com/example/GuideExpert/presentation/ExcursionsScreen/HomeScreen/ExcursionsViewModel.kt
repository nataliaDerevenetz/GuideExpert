package com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.GuideExpert.domain.GetExcursionByFiltersUseCase
import com.example.GuideExpert.domain.GetFiltersBarUseCase
import com.example.GuideExpert.domain.GetFiltersCategoriesUseCase
import com.example.GuideExpert.domain.GetFiltersDurationUseCase
import com.example.GuideExpert.domain.GetFiltersGroupsUseCase
import com.example.GuideExpert.domain.GetFiltersSortUseCase
import com.example.GuideExpert.domain.GetSortDefaultUseCase
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.Filter
import com.example.GuideExpert.domain.models.Filters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface ExcursionsUiEvent {
    data class OnClickFavoriteExcursion(val excursion: Excursion) : ExcursionsUiEvent
    data object OnLoadExcursionsFilters : ExcursionsUiEvent
    data object OnChangeFilters : ExcursionsUiEvent

}


@HiltViewModel
class ExcursionsViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val getExcursionByFiltersUseCase: GetExcursionByFiltersUseCase,
    val getFiltersBarUseCase: GetFiltersBarUseCase,
    val getFiltersDurationUseCase: GetFiltersDurationUseCase,
    val getFiltersSortUseCase: GetFiltersSortUseCase,
    val getFiltersGroupsUseCase: GetFiltersGroupsUseCase,
    val getFiltersCategoriesUseCase: GetFiltersCategoriesUseCase,
    val getSortDefaultUseCase: GetSortDefaultUseCase
) : ViewModel() {

    private val _effectChannel = Channel<SnackbarEffect>()
    val effectFlow: Flow<SnackbarEffect> = _effectChannel.receiveAsFlow()

    val sortDefault = getSortDefaultUseCase()
    private val _sortState = MutableStateFlow(sortDefault)
    val sortState: StateFlow<Int> = _sortState

    private val _uiPagingState = MutableStateFlow<PagingData<Excursion>>(PagingData.empty())
    val uiPagingState: StateFlow<PagingData<Excursion>> = _uiPagingState.asStateFlow()

    private var oldFilters:Filters = Filters(sortDefault, listOf(), listOf(), listOf())
    private val defaultFilters:Filters = Filters(sortDefault, listOf(), listOf(), listOf())

    private val _changeFilter = MutableStateFlow(defaultFilters)
    val changeFilter: StateFlow<Filters> = _changeFilter


    fun handleEvent(event: ExcursionsUiEvent) {
        viewModelScope.launch {
            when (event) {
                is ExcursionsUiEvent.OnLoadExcursionsFilters -> loadExcursionsFilters()
                is ExcursionsUiEvent.OnClickFavoriteExcursion -> setFavoriteExcursion(event.excursion)
                is ExcursionsUiEvent.OnChangeFilters -> changedFilters()
            }
        }
    }

    fun getFiltersBar():List<Filter> {
        return getFiltersBarUseCase()
    }

    fun getFiltersDuration():List<Filter> {
        return getFiltersDurationUseCase()
    }

    fun getFiltersSort():List<Filter> {
        return getFiltersSortUseCase()
    }

    fun getFiltersGroups():List<Filter> {
        return getFiltersGroupsUseCase()
    }

    fun getFiltersCategories():List<Filter> {
        return getFiltersCategoriesUseCase()
    }

    private fun changedFilters() {
        val filters = Filters(sortState.value,
            getFiltersCategories().filter { it.enabled.value}.map{it.id},
            getFiltersDuration().filter { it.enabled.value}.map{it.id},
            getFiltersGroups().filter { it.enabled.value}.map{it.id})
        _changeFilter.update {filters}
    }


    fun resetFilters() {
        _sortState.value = sortDefault
        val filtersBar = getFiltersBar()
        filtersBar.map { it.enabled.value = false }

        val filtersCategories = getFiltersCategories()
        filtersCategories.map { it.enabled.value = false }

        val filtersGroups = getFiltersGroups()
        filtersGroups.map { it.enabled.value = false }

        val filtersDuration = getFiltersDuration()
        filtersDuration.map { it.enabled.value = false }

        val filtersSort = getFiltersSort()
        filtersSort.map { it.enabled.value = false }
    }

    fun isChangedFilters(): Boolean {
        if (oldFilters.sort != sortState.value) return true
        if (oldFilters.categories != getFiltersCategories().filter { it.enabled.value}.map{it.id}) return true
        if (oldFilters.duration != getFiltersDuration().filter { it.enabled.value}.map{it.id}) return true
        if (oldFilters.group != getFiltersGroups().filter { it.enabled.value}.map{it.id}) return true
        return false
    }

    fun isChangedDefaultFilters(): Boolean {
        if (defaultFilters.sort != sortState.value) return true
        if (defaultFilters.categories != getFiltersCategories().filter { it.enabled.value}.map{it.id}) return true
        if (defaultFilters.duration != getFiltersDuration().filter { it.enabled.value}.map{it.id}) return true
        if (defaultFilters.group != getFiltersGroups().filter { it.enabled.value}.map{it.id}) return true
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

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun loadExcursionsFilters() {
        Log.d("TAG","loadExcursionsFilters")
        changeFilter.flatMapLatest{
            getExcursionByFiltersUseCase(changeFilter.value)
        }.cachedIn(viewModelScope).collectLatest{
            _uiPagingState.value = it
        }
    }

    suspend fun sendEffectFlow(message: String, actionLabel: String? = null) {
        _effectChannel.send(SnackbarEffect.ShowSnackbar(message))
    }

    init {
        handleEvent(ExcursionsUiEvent.OnLoadExcursionsFilters)
    }


}
