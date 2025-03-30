package com.example.GuideExpert.presentation.ProfileScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ProfileScreen(
    snackbarHostState: SnackbarHostState,
    onChangeVisibleBottomBar: (Boolean) -> Unit,
    onNavigateToHome:() ->Unit
) {
    NavigationProfileScreen(snackbarHostState,onChangeVisibleBottomBar = onChangeVisibleBottomBar,
        onNavigateToHome = onNavigateToHome)
}

