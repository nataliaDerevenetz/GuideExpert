package com.example.GuideExpert.presentation.ProfileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.GuideExpert.presentation.ProfileScreen.ProfileMainScreen.ProfileViewModel


@Composable
fun ProfileScreen(
    snackbarHostState: SnackbarHostState,
    onChangeVisibleBottomBar: (Boolean) -> Unit,
    onNavigateToHome:() ->Unit
) {
    NavigationProfileScreen(snackbarHostState,onChangeVisibleBottomBar = onChangeVisibleBottomBar,
        onNavigateToHome = onNavigateToHome)
}

