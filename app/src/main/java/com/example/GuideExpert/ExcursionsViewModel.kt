package com.example.GuideExpert

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ExcursionsViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {


    private val excursionDetail = ExcursionDetail.from(savedStateHandle)

    private val _excursion = MutableStateFlow(excursionDetail.excursion)

    val excursion = _excursion.asStateFlow()

    val excursionData = userInfoRepository.getExcursionInfo(excursionDetail.excursion.id).asLiveData()


}