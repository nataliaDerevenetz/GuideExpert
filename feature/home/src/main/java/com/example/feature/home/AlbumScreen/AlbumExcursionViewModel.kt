package com.example.feature.home.AlbumScreen

import androidx.lifecycle.ViewModel
import com.example.core.domain.repository.ExcursionsRepository
import com.example.core.models.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AlbumExcursionViewModel @Inject constructor(
    private val excursionsRepository: ExcursionsRepository,
) : ViewModel() {

    fun getImages(excursionId:Int):Flow<List<Image>>  = excursionsRepository.getImagesExcursion(excursionId)//getImagesExcursionDataUseCase(excursionId)

    fun getImage(imageId:Int):Flow<Image>  = excursionsRepository.getImageExcursion(imageId)
}