package com.example.GuideExpert.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.feature.home.navigateToHome
import com.example.feature.favorites.navigateToFavorites
import com.example.feature.profile.navigateToProfile
import com.example.GuideExpert.presentation.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
    time: String?,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): AppState {
    return remember(
        time,
        navController,
        coroutineScope,
    ) {
        AppState(
            time = time,
            navController = navController,
            coroutineScope = coroutineScope,
        )
    }
}
@Stable
class AppState(
    var time:String?,
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
) {
    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)
            return currentEntry.value?.destination
        }


    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTest() {
        navController.navigateToFavorites(navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        })
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (topLevelDestination) {
            TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
            TopLevelDestination.FAVORITES -> navController.navigateToFavorites(topLevelNavOptions)
            TopLevelDestination.PROFILE -> navController.navigateToProfile(topLevelNavOptions)
        }
    }

    var timeState = mutableStateOf(time)

    val bottomBarState = mutableStateOf(true)

    fun changeVisibleBottomBar(visible:Boolean) {
        bottomBarState.value = visible
    }

    val isLightStatusBar = mutableStateOf(true)

    fun setLightStatusBar(isLight: Boolean) {
        isLightStatusBar.value = isLight
    }

}