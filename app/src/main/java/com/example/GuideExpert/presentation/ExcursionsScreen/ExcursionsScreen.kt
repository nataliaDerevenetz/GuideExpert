package com.example.GuideExpert.presentation.ExcursionsScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState


@Composable
fun ExcursionsScreen(
    snackbarHostState: SnackbarHostState,
    onChangeVisibleBottomBar: (Boolean) -> Unit,
    onNavigateToProfile: () -> Unit,
    innerPadding: PaddingValues,
    isLight: MutableState<Boolean>
) {
    NavigationHomeScreen(snackbarHostState = snackbarHostState,onChangeVisibleBottomBar =onChangeVisibleBottomBar,
        onNavigateToProfile = onNavigateToProfile,innerPadding = innerPadding,isLight= isLight)
}
