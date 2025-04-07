package com.example.GuideExpert.presentation.FavoriteScreen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.GuideExpert.presentation.FavoriteScreen.FavotiteMainScreen.Favorites
import com.example.GuideExpert.presentation.ProfileScreen.ProfileInfo
import kotlinx.serialization.Serializable

@Serializable
object FavoriteList

@Composable
fun NavigationFavoriteScreen(snackbarHostState: SnackbarHostState,
                            onChangeVisibleBottomBar: (Boolean) -> Unit)  {
    val navController = rememberNavController()

    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    NavHost(navController = navController, startDestination = FavoriteList) {
        favoriteDestination(snackbarHostState,viewModelStoreOwner,onChangeVisibleBottomBar)
    }
}

fun NavGraphBuilder.favoriteDestination(snackbarHostState : SnackbarHostState,
                                       viewModelStoreOwner: ViewModelStoreOwner,
                                       onChangeVisibleBottomBar: (Boolean) -> Unit,
)
{
    composable<FavoriteList> {
        onChangeVisibleBottomBar(true)
        Favorites(snackbarHostState,viewModel = hiltViewModel(viewModelStoreOwner))
    }
}
