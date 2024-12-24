package com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.GuideExpert.domain.GetAllExcursionsUseCase
import com.example.GuideExpert.domain.GetExcursionByQueryUseCase
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.FilterQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SortParams(
    val relevancy: String = "relevancy",
    val popularity: String = "popularity",
)

data class QueryWithFilter(
    val query: String,
    val sort: String
)

sealed interface ExcursionListSearchUIState{
    object Idle:ExcursionListSearchUIState
    object Loading:ExcursionListSearchUIState
    object Data:ExcursionListSearchUIState
    data class Error(val error: String):ExcursionListSearchUIState
}


data class ExcursionsSearchUIState(
    val contentState: ExcursionListSearchUIState = ExcursionListSearchUIState.Idle
)


sealed interface SearchEvent {
    object GetSearchExcursions : SearchEvent
    data class SetSearchText(val text:String) : SearchEvent
    data class SetStateListSearch(val state:ExcursionListSearchUIState) : SearchEvent
    data class OnClickFavoriteExcursion(val excursion: Excursion) : SearchEvent
}
/*
sealed class SnackbarEffect {
    data class ShowSnackbar(val message: String, val actionLabel: String? = null) : SnackbarEffect()
}*/

@HiltViewModel
class ExcursionSearchViewModel @Inject constructor(
   // private val excursionRepository: ExcursionRepository,
    val getExcursionByQueryUseCase: GetExcursionByQueryUseCase,
    private val state: SavedStateHandle
) : ViewModel() {
    //  val excursions: Flow<List<Excursion>> = excursionRepository.getExcursions()

    private var searchText by mutableStateOf("")


    private val _uiPagingState = MutableStateFlow<PagingData<Excursion>>(PagingData.empty())
    val uiPagingState: StateFlow<PagingData<Excursion>> = _uiPagingState.asStateFlow()


    private val sortParamsFlow = MutableStateFlow(SortParams().relevancy)


    private val _stateView = MutableStateFlow<ExcursionsSearchUIState>(ExcursionsSearchUIState())
    val stateView: StateFlow<ExcursionsSearchUIState> = _stateView.asStateFlow()



    private val _effectChannel = Channel<SnackbarEffect>()
    val effectFlow: Flow<SnackbarEffect> = _effectChannel.receiveAsFlow()


    @OptIn(FlowPreview::class)
    private val currentQueryFlow = snapshotFlow { searchText }
        .debounce(500L)
        .filter { it.isNotEmpty() }
        .distinctUntilChanged()
        .onEach {
            //  _uiPagingState.update { PagingData.empty()}
            onEvent(SearchEvent.SetStateListSearch(ExcursionListSearchUIState.Loading))
        }
        .flowOn(Dispatchers.IO)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), replay = 1)

    fun onEvent(event: SearchEvent) {
        viewModelScope.launch {
            when (event) {
                is SearchEvent.GetSearchExcursions -> {
                    getSearchExcursions()
                }
                is SearchEvent.SetSearchText -> {
                    setCurrentText(event.text)
                }

                is SearchEvent.SetStateListSearch -> {
                    updateExcursionListSearchUIState(event.state)
                }

                is SearchEvent.OnClickFavoriteExcursion -> setFavoriteExcursion(event.excursion)
            }
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun getSearchExcursions() {
        combine(
            currentQueryFlow,
            sortParamsFlow,
        ) { (query, sort) ->
            FilterQuery(query, sort)
        }.flowOn(Dispatchers.IO)
            .flatMapLatest {
                Log.d("TAG", "get list ::${it.query}")
                getExcursionByQueryUseCase(it)
                //excursionRepository.getExcursionList()
            }.cachedIn(viewModelScope).collect {
                if(_stateView.value.contentState is ExcursionListSearchUIState.Loading){
                    updateExcursionListSearchUIState(ExcursionListSearchUIState.Data)
                }
                _uiPagingState.value = it
                Log.d("TAG", "ExcursionListSearchUIState.Data")
            }
    }


    private fun updateExcursionListSearchUIState(state : ExcursionListSearchUIState) {
        _stateView.update { it.copy(contentState = state) }
    }

    init {
        searchText = state.getLiveData("query", "").value.toString()
        onEvent(SearchEvent.GetSearchExcursions)
    }



    fun setCurrentText(query: String) {
        state["query"] = query
        searchText = state.getLiveData("query", "").value.toString()
    }


    suspend fun sendEffectFlow(message: String, actionLabel: String? = null) {
        _effectChannel.send(SnackbarEffect.ShowSnackbar(message))
    }


     fun setFavoriteExcursion(excursion: Excursion) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("CLICK","setFavoriteExcursionUseCase")
            // setFavoriteExcursionUseCase(note)

        }
    }



}