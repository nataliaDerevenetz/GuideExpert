package com.example.GuideExpert

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.GuideExpert.domain.GetExcursionAllUseCase
import com.example.GuideExpert.domain.models.Excursion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class ExcursionsViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    getExcursionAllUseCase: GetExcursionAllUseCase
) : ViewModel() {

    private val _allExcursion = MutableStateFlow(emptyList<Excursion>())
    val allExcursionState: StateFlow<List<Excursion>> = _allExcursion.asStateFlow()


    init {
        _allExcursion.update { getExcursionAllUseCase() }
    }

    //val allExcursion = getExcursionAllUseCase()






}
