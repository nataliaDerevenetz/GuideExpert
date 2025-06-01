package com.example.feature.home.AlbumScreen

import androidx.lifecycle.ViewModel
import com.example.core.domain.GetImageExcursionUseCase
import com.example.core.domain.GetImagesExcursionDataUseCase
import com.example.core.models.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AlbumExcursionViewModel @Inject constructor(
    val getImagesExcursionDataUseCase: GetImagesExcursionDataUseCase,
    val getImageExcursionUseCase: GetImageExcursionUseCase
) : ViewModel() {

    fun getImages(excursionId:Int):Flow<List<Image>>  = getImagesExcursionDataUseCase(excursionId)

    fun getImage(imageId:Int):Flow<Image>  = getImageExcursionUseCase(imageId)
}