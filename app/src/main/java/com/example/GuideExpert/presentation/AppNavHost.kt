package com.example.GuideExpert.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeBaseRoute
import com.example.GuideExpert.presentation.ExcursionsScreen.homeScreen
import com.example.GuideExpert.presentation.ExcursionsScreen.navigateToAlbum
import com.example.GuideExpert.presentation.ExcursionsScreen.navigateToExcursionDetail
import com.example.GuideExpert.presentation.ExcursionsScreen.navigateToImage
import com.example.GuideExpert.presentation.FavoriteScreen.favoritesScreen
import com.example.GuideExpert.presentation.ProfileScreen.navigateToProfile
import com.example.GuideExpert.presentation.ProfileScreen.profileScreen

@Composable
fun AppNavHost(
    appState: AppState,
    innerPadding: PaddingValues,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = HomeBaseRoute,
        modifier = modifier,
    ) {
        homeScreen(
            innerPadding = innerPadding,
            snackbarHostState = snackbarHostState,
            onNavigateToExcursion = navController::navigateToExcursionDetail,
            onNavigateToAlbum =  navController::navigateToAlbum,
            onNavigateToImage = navController::navigateToImage,
            onNavigateToBack = navController::popBackStack,
            onNavigateToProfileInfo = navController::navigateToProfile,
            onChangeVisibleBottomBar = {visible:Boolean -> appState.changeVisibleBottomBar(visible)},
            onSetLightStatusBar = {isLight:Boolean -> appState.setLightStatusBar(isLight)}
        )
        favoritesScreen(
          // onTopicClick = navController::navigateToInterests,
          //  onShowSnackbar = onShowSnackbar,
        )
        profileScreen(
          //  onBackClick = navController::popBackStack,
          //  onInterestsClick = { appState.navigateToTopLevelDestination(INTERESTS) },
          //  onTopicClick = navController::navigateToInterests,
        )

    }
}
