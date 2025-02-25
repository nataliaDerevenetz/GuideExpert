package com.example.GuideExpert.presentation.ExcursionsScreen.AlbumScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.GuideExpert.domain.models.Excursion

@Composable
fun AlbumExcursionScreen(
    excursionId: Int,
    navigateToImage: (Excursion) -> Unit = {},
    //  viewModel: ExcursionsViewModel = hiltViewModel()
) {

    Text("Album $excursionId")
}