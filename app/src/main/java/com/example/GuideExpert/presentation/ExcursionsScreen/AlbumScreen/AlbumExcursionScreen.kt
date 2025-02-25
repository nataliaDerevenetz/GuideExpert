package com.example.GuideExpert.presentation.ExcursionsScreen.AlbumScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.Image
import com.example.GuideExpert.presentation.ExcursionsScreen.DetailScreen.NetworkImageCarousel
import kotlinx.coroutines.flow.Flow

@Composable
fun AlbumExcursionScreen(
    excursionId: Int,
    navigateToImage: (Int) -> Unit = {},
    viewModel: AlbumExcursionViewModel = hiltViewModel(),
    images: Flow<List<Image>> = viewModel.getImages(excursionId)
) {

    val excursionImages by images.collectAsStateWithLifecycle(null)
    Text("Album $excursionId")
    excursionImages?.let {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(200.dp),
            verticalItemSpacing = 4.dp,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                items(excursionImages!!) {
                    photo -> NetworkImageCarousel(photo.url, "",500,250,navigateToImage,photo.id)
                }

            }, modifier = Modifier.fillMaxSize()
        )

    }
}