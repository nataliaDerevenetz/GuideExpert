package com.example.GuideExpert.presentation.ExcursionsScreen.DetailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.domain.GetExcursionDataUseCase
import com.example.GuideExpert.domain.GetExcursionDetailUseCase
import com.example.GuideExpert.domain.GetFiltersGroupsUseCase
import com.example.GuideExpert.domain.GetImagesExcursionDataUseCase
import com.example.GuideExpert.domain.models.ExcursionData
import com.example.GuideExpert.domain.models.Filter
import com.example.GuideExpert.domain.models.Image
import com.example.GuideExpert.presentation.ExcursionsScreen.ExcursionDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ExcursionInfoUIState{
    object Idle:ExcursionInfoUIState
    object Loading:ExcursionInfoUIState
    object Data:ExcursionInfoUIState
    data class Error(val error: String):ExcursionInfoUIState
}

data class UIState(
    val contentState: ExcursionInfoUIState = ExcursionInfoUIState.Idle
)

sealed interface ExcursionDetailUiEvent {
    data object OnLoadExcursionInfo : ExcursionDetailUiEvent
}


@HiltViewModel
class ExcursionDetailViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val getExcursionDetailUseCase: GetExcursionDetailUseCase,
    val getExcursionDataUseCase: GetExcursionDataUseCase,
    val getImagesExcursionDataUseCase: GetImagesExcursionDataUseCase,
    val getFiltersGroupsUseCase: GetFiltersGroupsUseCase,
) : ViewModel() {


    private val excursionDetail = ExcursionDetail.from(savedStateHandle)

    private val _stateView = MutableStateFlow<UIState>(UIState())
    val stateView: StateFlow<UIState> = _stateView.asStateFlow()

    val excursion: Flow<ExcursionData?> = getExcursionDataUseCase(excursionDetail.excursion.id)

    val images: Flow<List<Image>> = getImagesExcursionDataUseCase(excursionDetail.excursion.id)

    fun getFiltersGroups():List<Filter> {
        return getFiltersGroupsUseCase()
    }
    fun handleEvent(event: ExcursionDetailUiEvent) {
        viewModelScope.launch {
            when (event) {
                is ExcursionDetailUiEvent.OnLoadExcursionInfo -> loadInfo()
            }
        }
    }

    private suspend fun loadInfo() {
        getExcursionDetailUseCase(excursionDetail.excursion.id).flowOn(Dispatchers.IO).collectLatest { resource ->
            when(resource) {
                is UIResources.Error -> {
                    _stateView.update { it.copy(contentState = ExcursionInfoUIState.Error(resource.message) ) } }
                is UIResources.Loading -> {
                    _stateView.update { it.copy(contentState = ExcursionInfoUIState.Loading ) } }
                is UIResources.Success -> {
                    _stateView.update { it.copy(contentState = ExcursionInfoUIState.Data ) }
                }
            }
        }
    }

    init {
        handleEvent(ExcursionDetailUiEvent.OnLoadExcursionInfo)
    }

}