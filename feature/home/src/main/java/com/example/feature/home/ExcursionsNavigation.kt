package com.example.feature.home

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.core.models.Excursion
import com.example.core.models.Image
import com.example.core.utils.serializableType
import com.example.core.utils.serializableTypeArray
import com.example.feature.home.AlbumScreen.AlbumExcursionScreen
import com.example.feature.home.AlbumScreen.ImageExcursionScreen
import com.example.feature.home.BookingScreen.BookingExcursionScreen
import com.example.feature.home.DetailScreen.ExcursionDetailScreen
import com.example.feature.home.ExcursionDetail.Companion.typeMap
import com.example.feature.home.HomeScreen.HomeScreen
import com.example.feature.home.ImageExcursion.Companion.typeMapImage
import com.example.notifications.DEEP_LINK_BASE_PATH
import com.example.notifications.DEEP_LINK_SCHEME_AND_HOST
import com.example.notifications.DEEP_LINK_URI_PATTERN
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf


@Serializable
data object HomeRoute

@Serializable data object HomeBaseRoute

@Serializable
data class AlbumExcursion(
    val excursionId:Int
)

@Serializable
data class BookingExcursion(
    val excursionId:Int
)

@Serializable
data class ImageExcursion(
    val imageId:Int,
    val excursionImages: List<Image>,
    val indexImage:Int
){
    companion object {
        val typeMapImage = mapOf(typeOf<List<Image>>() to serializableTypeArray<Image>())
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

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(route = HomeRoute, navOptions)


fun NavGraphBuilder.homeScreen(
    innerPadding: PaddingValues,
    snackbarHostState: SnackbarHostState,
    onNavigateToExcursion: (Excursion) -> Unit,
    onNavigateToAlbum: (Int) -> Unit,
    onNavigateToImage: (Int, List<Image>, Int) -> Unit,
    onNavigateToBack:() -> Unit,
    onNavigateToProfileInfo: () -> Unit,
    onChangeVisibleBottomBar: (Boolean) -> Unit,
    onSetLightStatusBar: (Boolean) -> Unit,
    onNavigateToBooking: (Int) -> Unit,
) {
    navigation<HomeBaseRoute>(startDestination = HomeRoute) {
        composable<HomeRoute> {
            onChangeVisibleBottomBar(true)
            onSetLightStatusBar(true)
            HomeScreen(snackbarHostState = snackbarHostState,navigateToExcursion = onNavigateToExcursion,navigateToProfileInfo = onNavigateToProfileInfo,innerPadding = innerPadding)
        }

        composable<ExcursionDetail>(typeMap = typeMap,
        //    deepLinks = listOf(
        //        navDeepLink<ExcursionDetail>(basePath = "$DEEP_LINK_SCHEME_AND_HOST/excursion")
        //    )

        ) {
            onChangeVisibleBottomBar(false)
            onSetLightStatusBar(true)
            ExcursionDetailScreen(onNavigateToAlbum,onNavigateToImage,onNavigateToBack,snackbarHostState,innerPadding,onNavigateToProfileInfo,onNavigateToBooking)
        }

        composable<TestRoute>(
            deepLinks = listOf(
                 navDeepLink {
                    uriPattern = "https://www.guide-expert.ru/test"
                },
            )

        ) {
            TestScreen()
        }

/*
        composable<TestRoute>(
            deepLinks = listOf(
                navDeepLink<TestRoute>(basePath = "$DEEP_LINK_SCHEME_AND_HOST/test")
            )
        ) {
            backStackEntry ->
            TestScreen(id = backStackEntry.toRoute<TestRoute>().id)
        }
*/

        composable<AlbumExcursion> {
                backStackEntry ->
            onChangeVisibleBottomBar(false)
            onSetLightStatusBar(true)
            val excursion = backStackEntry.toRoute<AlbumExcursion>()
            AlbumExcursionScreen(excursionId = excursion.excursionId,navigateToImage=onNavigateToImage)
        }

        composable<ImageExcursion>(typeMap = typeMapImage) {
                backStackEntry ->
            onChangeVisibleBottomBar(false)
            onSetLightStatusBar(false)
            val imageExcursion = backStackEntry.toRoute<ImageExcursion>()
            ImageExcursionScreen(imageExcursion = imageExcursion)
        }

        composable<BookingExcursion> {
                backStackEntry ->
            onChangeVisibleBottomBar(false)
            onSetLightStatusBar(true)
            val excursion = backStackEntry.toRoute<BookingExcursion>()
            BookingExcursionScreen(excursionId = excursion.excursionId,innerPadding,snackbarHostState = snackbarHostState,navigateToBack = onNavigateToBack )
        }
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

fun NavController.navigateToBooking(excursionId: Int) {
    navigate(route = BookingExcursion(excursionId)){
        launchSingleTop=true
    }
}

fun NavController.navigateToImage(imageId: Int,excursionImages: List<Image>,indexImage:Int) {
    navigate(route = ImageExcursion(imageId,excursionImages,indexImage)){
        launchSingleTop=true
    }
}


//@Serializable data class TestRoute(val id: String)
@Serializable data object TestRoute

@Composable
internal fun TestScreen(id: String = "") {
    Log.d("TEST", id.toString())
    Text("Test")
}