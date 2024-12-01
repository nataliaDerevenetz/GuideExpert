package com.example.GuideExpert

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.GuideExpert.domain.GetExcursionAllUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ExcursionsViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    getExcursionAllUseCase: GetExcursionAllUseCase
) : ViewModel() {

     val allExcursion = getExcursionAllUseCase()

}
