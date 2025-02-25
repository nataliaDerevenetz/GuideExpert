package com.example.GuideExpert.presentation.ExcursionsScreen.DetailScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.GuideExpert.data.repository.UIResources
import com.example.GuideExpert.domain.GetExcursionDataUseCase
import com.example.GuideExpert.domain.GetExcursionDetailUseCase
import com.example.GuideExpert.domain.GetImagesExcursionDataUseCase
import com.example.GuideExpert.domain.models.ExcursionData
import com.example.GuideExpert.domain.models.Image
import com.example.GuideExpert.presentation.ExcursionsScreen.ExcursionDetail
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.ExcursionListSearchUIState
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.ExcursionsSearchUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
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

@HiltViewModel
class ExcursionDetailViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val getExcursionDetailUseCase: GetExcursionDetailUseCase,
    val getExcursionDataUseCase: GetExcursionDataUseCase,
    val getImagesExcursionDataUseCase: GetImagesExcursionDataUseCase
) : ViewModel() {


    private val excursionDetail = ExcursionDetail.from(savedStateHandle)

    private val _stateView = MutableStateFlow<UIState>(UIState())
    val stateView: StateFlow<UIState> = _stateView.asStateFlow()


    val excursion: Flow<ExcursionData?> = getExcursionDataUseCase(excursionDetail.excursion.id)

    val images: Flow<List<Image>> = getImagesExcursionDataUseCase(excursionDetail.excursion.id)

    // private val _excursion = MutableStateFlow(excursionDetail.excursion)

   // val excursion = _excursion.asStateFlow()

   // val excursionData = getExcursionDetailUseCase(excursionDetail.excursion.id).asLiveData()

//    val excursionData2 = getExcursionDetailUseCase(excursionDetail.excursion.id)
 //       .shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), replay = 1)

   // val images: Flow<List<Image>> = getImagesByExcursion(excursionDetail.excursion.id)


    private suspend fun loadInfo() {
        Log.d("TAG","loadInfo")
        getExcursionDetailUseCase(excursionDetail.excursion.id).flowOn(Dispatchers.IO).collectLatest { resource ->
            when(resource) {
                is UIResources.Error -> {
                    _stateView.update { it.copy(contentState = ExcursionInfoUIState.Error(resource.message) ) }
                    Log.d("TAG2","loadInfo Error")}
                is UIResources.Loading -> {
                    _stateView.update { it.copy(contentState = ExcursionInfoUIState.Loading ) }
                    Log.d("TAG2","loadInfo Loading")}
                is UIResources.Success -> {
                    Log.d("TAG2","loadInfo Success")
                    Log.d("TAG2","TITLE ${resource.data.title}")
                    _stateView.update { it.copy(contentState = ExcursionInfoUIState.Data ) }
                    // _configApp.update { resource.data }
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            loadInfo()
        }
    }

}