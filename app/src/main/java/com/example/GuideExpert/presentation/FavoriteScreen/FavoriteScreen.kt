package com.example.GuideExpert.presentation.FavoriteScreen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable

@Composable
fun FavoriteScreen(
    snackbarHostState: SnackbarHostState,
    onChangeVisibleBottomBar: (Boolean) -> Unit,
) {
    NavigationFavoriteScreen(snackbarHostState,onChangeVisibleBottomBar = onChangeVisibleBottomBar)
}