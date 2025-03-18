package com.example.GuideExpert.presentation.ProfileScreen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Serializable
object Screen1

@Serializable
data class Screen2(
    var name : String = "test"
)


@Composable
fun NavigationProfileScreen(snackbarHostState: SnackbarHostState,
                            onChangeVisibleBottomBar: (Boolean) -> Unit,)  {
    val navController = rememberNavController()

    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    NavHost(navController = navController, startDestination = Screen1) {
        val onNavigateToScreen2 = { navController.navigateToScreen2("Ok it Screen2")}
        val onNavigateToYandex = { navController.navigateToYandex()}
        profileDestination(snackbarHostState,onNavigateToScreen2,onNavigateToYandex, viewModelStoreOwner,onChangeVisibleBottomBar)
    }

}

fun NavGraphBuilder.profileDestination(snackbarHostState :SnackbarHostState,
                                       onNavigateToScreen2: () -> Unit,
                                       onNavigateToYandex: () -> Unit,
                                       viewModelStoreOwner: ViewModelStoreOwner,
                                       onChangeVisibleBottomBar: (Boolean) -> Unit,
)
{
    composable<Screen1> {
        onChangeVisibleBottomBar(false)
        Screen1View(snackbarHostState,onNavigateToYandex,onNavigateToScreen2, viewModel = hiltViewModel(viewModelStoreOwner)) }
    composable<Screen2> {
        onChangeVisibleBottomBar(false)
        Screen2View(onNavigateToYandex,viewModel = hiltViewModel(viewModelStoreOwner)) }
    activity("loginYandex") { activityClass = ProfileYandexActivity::class }
}

fun NavController.navigateToScreen2(str: String) {
    navigate(route = Screen2(str)){
        launchSingleTop=true
    }
}

fun NavController.navigateToYandex() {
    navigate(route ="loginYandex"){
        launchSingleTop=true
    }
}

