package com.example.feature.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.activity
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.feature.profile.EditorProfileScreen.EditorProfileScreen
import com.example.feature.profile.ProfileMainScreen.ProfileInfo
import kotlinx.serialization.Serializable

@Serializable data object ProfileRoute

@Serializable data object ProfileBaseRoute

@Serializable object EditorProfile


fun NavController.navigateToProfile(navOptions: NavOptions? = null) = navigate(route = com.example.feature.profile.ProfileRoute, navOptions)

@RequiresApi(Build.VERSION_CODES.P)
fun NavGraphBuilder.profileScreen(
    innerPadding: PaddingValues,
    snackbarHostState: SnackbarHostState,
    onChangeVisibleBottomBar: (Boolean) -> Unit,
    onNavigateToEditorProfile: () -> Unit,
    onNavigateToYandex: () -> Unit,
    onNavigateToBack: () -> Unit,
    onNavigateToProfile: () -> Unit,
) {
    navigation<ProfileBaseRoute>(startDestination = com.example.feature.profile.ProfileRoute) {
        composable<ProfileRoute> {
            onChangeVisibleBottomBar(true)
            ProfileInfo(
                snackbarHostState,
                onNavigateToYandex,
                onNavigateToEditorProfile,
                onNavigateToBack,
                innerPadding
            )
        }
        composable<EditorProfile> {
            onChangeVisibleBottomBar(false)
            EditorProfileScreen(snackbarHostState,onNavigateToProfile,innerPadding) }
        activity("loginYandex") { activityClass = com.example.feature.profile.YandexActivity.ProfileYandexActivity::class }
    }
}

fun NavController.navigateToEditorProfile() {
    navigate(route = com.example.feature.profile.EditorProfile){
        launchSingleTop=true
    }
}

fun NavController.navigateToYandex() {
    navigate(route ="loginYandex"){
        launchSingleTop=true
    }
}

