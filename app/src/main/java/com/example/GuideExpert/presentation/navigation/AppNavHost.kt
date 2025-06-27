package com.example.GuideExpert.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.example.GuideExpert.presentation.AppState
import com.example.feature.favorites.FavoritesBaseRoute
import com.example.feature.home.homeScreen
import com.example.feature.home.navigateToAlbum
import com.example.feature.home.navigateToBooking
import com.example.feature.home.navigateToExcursionDetail
import com.example.feature.home.navigateToHome
import com.example.feature.home.navigateToImage
import com.example.feature.favorites.favoritesScreen
import com.example.feature.profile.navigateToEditorProfile
import com.example.feature.profile.navigateToProfile
import com.example.feature.profile.navigateToYandex
import com.example.feature.profile.profileScreen
import com.example.feature.home.HomeBaseRoute

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AppNavHost(
    appState: AppState,
    innerPadding: PaddingValues,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController


   // var f by remember { mutableStateOf(false) }
   // val startDestination = if(f) HomeBaseRoute as Any else FavoritesBaseRoute

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
            onNavigateToProfileInfo = {navController.navigateToProfile(navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            })},
            onChangeVisibleBottomBar = { visible: Boolean -> appState.changeVisibleBottomBar(visible) },
            onSetLightStatusBar = { isLight: Boolean -> appState.setLightStatusBar(isLight) },
            onNavigateToBooking =  navController::navigateToBooking,
        )
        favoritesScreen(
            innerPadding = innerPadding,
            snackbarHostState = snackbarHostState,
            onChangeVisibleBottomBar = { visible: Boolean -> appState.changeVisibleBottomBar(visible) },
            onNavigateToExcursion = navController::navigateToExcursionDetail
        )
        profileScreen(
            innerPadding = innerPadding,
            snackbarHostState = snackbarHostState,
            onChangeVisibleBottomBar = { visible: Boolean ->
                appState.changeVisibleBottomBar(
                    visible
                )
            },
            onNavigateToEditorProfile = navController::navigateToEditorProfile,
            onNavigateToYandex = navController::navigateToYandex,
            onNavigateToBack = {navController.navigateToHome(navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                   saveState = true
               }
               /* popUpTo(TopLevelDestination.HOME.route) {
                    saveState = true
                }*/
                launchSingleTop = true
                restoreState = true
            })},
            onNavigateToProfile = navController::popBackStack
        )

    }
}
