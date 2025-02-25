package com.example.GuideExpert.presentation.ExcursionsScreen.AlbumScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.GuideExpert.domain.GetExcursionDataUseCase
import com.example.GuideExpert.domain.GetExcursionDetailUseCase
import com.example.GuideExpert.domain.GetImagesExcursionDataUseCase
import com.example.GuideExpert.domain.models.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AlbumExcursionViewModel @Inject constructor(
    val getImagesExcursionDataUseCase: GetImagesExcursionDataUseCase
) : ViewModel() {
    
    fun getImages(excursionId:Int):Flow<List<Image>>  = getImagesExcursionDataUseCase(excursionId)
}