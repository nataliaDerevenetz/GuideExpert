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
import com.example.GuideExpert.domain.models.Image
import com.example.GuideExpert.presentation.ExcursionsScreen.AlbumScreen.AlbumExcursionScreen
import com.example.GuideExpert.presentation.ExcursionsScreen.AlbumScreen.ImageExcursionScreen
import com.example.GuideExpert.presentation.ExcursionsScreen.DetailScreen.ExcursionDetailScreen
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.HomeScreen
import com.example.GuideExpert.utils.serializableType
import com.example.GuideExpert.utils.serializableTypeArray
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
object ExcursionSearchScreen

@Serializable
data class AlbumExcursion(
    val excursionId:Int
)

@Serializable
data class ImageExcursion(
    val imageId:Int,
    val excursionImages: List<Image>,
    val indexImage:Int
){
    companion object {
        val typeMap = mapOf(typeOf<List<Image>>() to serializableTypeArray<Image>())

        fun from(savedStateHandle: SavedStateHandle) =
            savedStateHandle.toRoute<ImageExcursion>(typeMap)
    }
}

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
    onChangeVisibleBottomBar: (Boolean) -> Unit,
    count :Int,
    onIcr :()->Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ExcursionSearchScreen) {
        val onNavigateToExcursion = { it: Excursion -> navController.navigateToExcursionDetail(excursion = it) }
        val onNavigateToAlbum = { excursionId: Int -> navController.navigateToAlbum(excursionId = excursionId) }
        val onNavigateToImage = { imageId: Int,excursionImages: List<Image>,indexImage:Int -> navController.navigateToImage(imageId = imageId,excursionImages=excursionImages,
            indexImage=indexImage) }
        excursionsDestination(snackbarHostState,onNavigateToExcursion,onNavigateToAlbum,onNavigateToImage,onChangeVisibleBottomBar,count,onIcr)
    }
}

fun NavGraphBuilder.excursionsDestination(snackbarHostState :SnackbarHostState,
                                          onNavigateToExcursion: (Excursion) -> Unit,
                                          onNavigateToAlbum: (Int) -> Unit,
                                          onNavigateToImage: (Int,List<Image>,Int) -> Unit,
                                          onChangeVisibleBottomBar: (Boolean) -> Unit,
                                          count: Int, onIcr:() -> Unit) {
    composable<ExcursionSearchScreen> {
        HomeScreen(snackbarHostState,onNavigateToExcursion,onChangeVisibleBottomBar=onChangeVisibleBottomBar)
    }
    composable<ExcursionDetail>(typeMap = ExcursionDetail.typeMap) {
        ExcursionDetailScreen(onNavigateToAlbum,onNavigateToImage,onChangeVisibleBottomBar=onChangeVisibleBottomBar,count, onIcr)
    }
    composable<AlbumExcursion> {
        backStackEntry ->
        val excursion = backStackEntry.toRoute<AlbumExcursion>()
        AlbumExcursionScreen(excursionId = excursion.excursionId,navigateToImage=onNavigateToImage,onChangeVisibleBottomBar=onChangeVisibleBottomBar)
    }
    composable<ImageExcursion>(typeMap = ImageExcursion.typeMap) {
        backStackEntry ->
        val imageExcursion = backStackEntry.toRoute<ImageExcursion>()
        ImageExcursionScreen(imageExcursion = imageExcursion,onChangeVisibleBottomBar=onChangeVisibleBottomBar)
    }
}

fun NavController.navigateToExcursionDetail(excursion: Excursion) {
    navigate(route = ExcursionDetail(excursion)){
        launchSingleTop=true
    }
}

fun NavController.navigateToAlbum(excursionId: Int) {
    navigate(route = AlbumExcursion(excursionId)){
        launchSingleTop=true
    }
}

fun NavController.navigateToImage(imageId: Int,excursionImages: List<Image>,indexImage:Int) {
    navigate(route = ImageExcursion(imageId,excursionImages,indexImage)){
        launchSingleTop=true
    }
}