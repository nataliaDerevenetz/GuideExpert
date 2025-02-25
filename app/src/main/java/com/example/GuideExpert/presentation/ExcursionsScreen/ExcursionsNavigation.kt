package com.example.GuideExpert.presentation.ExcursionsScreen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.presentation.ExcursionsScreen.DetailScreen.ExcursionDetailScreen
import com.example.GuideExpert.presentation.ExcursionsScreen.AlbumScreen.AlbumExcursionScreen
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.HomeScreen
import com.example.GuideExpert.utils.serializableType
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
object ExcursionSearchScreen

@Serializable
data class AlbumExcursion(
    val excursionId:Int
)


@Serializable
class ExcursionDetail(val excursion : Excursion) {
    companion object {
        val typeMap = mapOf(typeOf<Excursion>() to serializableType<Excursion>())

        fun from(savedStateHandle: SavedStateHandle) =
            savedStateHandle.toRoute<ExcursionDetail>(typeMap)
    }
}

@Composable
fun NavigationHomeScreen(
    snackbarHostState : SnackbarHostState,
    count :Int,
    onIcr :()->Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ExcursionSearchScreen) {
        val onNavigateToExcursion = { it: Excursion -> navController.navigateToExcursionDetail(excursion = it) }
        val onNavigateToAlbum = { excursionId: Int -> navController.navigateToAlbum(excursionId = excursionId) }
        excursionsDestination(snackbarHostState,onNavigateToExcursion,onNavigateToAlbum,count,onIcr)
    }
}

fun NavGraphBuilder.excursionsDestination(snackbarHostState :SnackbarHostState,
                                          onNavigateToExcursion: (Excursion) -> Unit,
                                          onNavigateToAlbum: (Int) -> Unit,
                                          count: Int, onIcr:() -> Unit) {
    composable<ExcursionSearchScreen> {
        HomeScreen(snackbarHostState,onNavigateToExcursion)
    }
    composable<ExcursionDetail>(typeMap = ExcursionDetail.typeMap) {
        ExcursionDetailScreen(onNavigateToAlbum,count, onIcr)
    }
    composable<AlbumExcursion> {
        backStackEntry ->
        val excursion = backStackEntry.toRoute<AlbumExcursion>()
        AlbumExcursionScreen(excursionId = excursion.excursionId)
    }
}

fun NavController.navigateToExcursionDetail(excursion: Excursion) {
    navigate(route = ExcursionDetail(excursion))
}

fun NavController.navigateToAlbum(excursionId: Int) {
    navigate(route = AlbumExcursion(excursionId))
}