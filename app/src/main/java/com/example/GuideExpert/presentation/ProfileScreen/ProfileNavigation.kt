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
import com.example.GuideExpert.presentation.ProfileScreen.ProfileMainScreen.ProfileInfo
import com.example.GuideExpert.presentation.ProfileScreen.YandexActivity.ProfileYandexActivity
import kotlinx.serialization.Serializable

@Serializable
object ProfileInfoScreen

@Serializable
data class Screen2(
    var name : String = "test"
)


@Composable
fun NavigationProfileScreen(snackbarHostState: SnackbarHostState,
                            onChangeVisibleBottomBar: (Boolean) -> Unit,
                            onNavigateToHome:()->Unit)  {
    val navController = rememberNavController()

    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    NavHost(navController = navController, startDestination = ProfileInfoScreen) {
        val onNavigateEditorProfile = { navController.navigateEditorProfile("Ok it Screen2")}
        val onNavigateToYandex = { navController.navigateToYandex()}
        val onNavigateToBack = { onNavigateToHome() }
        profileDestination(snackbarHostState,onNavigateEditorProfile,onNavigateToYandex,onNavigateToBack, viewModelStoreOwner,onChangeVisibleBottomBar)
    }
}

fun NavGraphBuilder.profileDestination(snackbarHostState :SnackbarHostState,
                                       onNavigateToEditorProfile: () -> Unit,
                                       onNavigateToYandex: () -> Unit,
                                       onNavigateToBack: () -> Unit,
                                       viewModelStoreOwner: ViewModelStoreOwner,
                                       onChangeVisibleBottomBar: (Boolean) -> Unit,
)
{
    composable<ProfileInfoScreen> {
        onChangeVisibleBottomBar(true)
        ProfileInfo(snackbarHostState,onNavigateToYandex,onNavigateToEditorProfile, onNavigateToBack,viewModel = hiltViewModel(viewModelStoreOwner)) }
    composable<Screen2> {
        onChangeVisibleBottomBar(false)
        Screen2View(onNavigateToYandex,viewModel = hiltViewModel(viewModelStoreOwner)) }
    activity("loginYandex") { activityClass = ProfileYandexActivity::class }
}

fun NavController.navigateEditorProfile(str: String) {
    navigate(route = Screen2(str)){
        launchSingleTop=true
    }
}

fun NavController.navigateToYandex() {
    navigate(route ="loginYandex"){
        launchSingleTop=true
    }
}

