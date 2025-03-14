package com.example.GuideExpert.presentation.ExcursionsScreen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable


@Composable
fun ExcursionsScreen(
    snackbarHostState: SnackbarHostState,
    onChangeVisibleBottomBar: (Boolean) -> Unit
) {
    NavigationHomeScreen(snackbarHostState = snackbarHostState,onChangeVisibleBottomBar =onChangeVisibleBottomBar)
}
