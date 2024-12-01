package com.example.GuideExpert

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.GuideExpert.domain.GetExcursionAllUseCase
import com.example.GuideExpert.domain.GetExcursionDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ExcursionDetailViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    getExcursionDetailUseCase: GetExcursionDetailUseCase
) : ViewModel() {


    private val excursionDetail = ExcursionDetail.from(savedStateHandle)

    private val _excursion = MutableStateFlow(excursionDetail.excursion)

    val excursion = _excursion.asStateFlow()

    val excursionData = getExcursionDetailUseCase(excursionDetail.excursion.id).asLiveData()

}