package com.example.GuideExpert.presentation.ExcursionsScreen

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.Image
import com.example.GuideExpert.presentation.ExcursionsScreen.DetailScreen.ExcursionDetailScreen
import com.example.GuideExpert.presentation.ExcursionsScreen.AlbumScreen.AlbumExcursionScreen
import com.example.GuideExpert.presentation.ExcursionsScreen.AlbumScreen.ImageExcursionScreen
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.HomeScreen
import com.example.GuideExpert.utils.serializableType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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
)


object NavigationHelpers {

    inline fun <reified T : Parcelable> parcelableListType(
        isNullableAllowed: Boolean = false,
        json: Json = Json,
    ) = object : NavType<List<T>>(isNullableAllowed = isNullableAllowed) {
        override fun get(bundle: Bundle, key: String): List<T>? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelableArrayList(key, T::class.java)
            } else {
                @Suppress("DEPRECATION")
                bundle.getParcelableArrayList(key)
            }
        }

        override fun parseValue(value: String): List<T> = json.decodeFromString(value)

        override fun serializeAsValue(value: List<T>): String = json.encodeToString(value)

        override fun put(bundle: Bundle, key: String, value: List<T>) {
            bundle.putParcelableArrayList(key, ArrayList(value))
        }
    }
}

val ImageNavType = object : NavType<List<Image>>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): List<Image>? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelableArrayList(key, Image::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelableArrayList(key)
        }
    }

    override fun parseValue(value: String): List<Image> {
        return Json.decodeFromString(value)
    }

    override fun serializeAsValue(value: List<Image>): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun put(bundle: Bundle, key: String, value: List<Image>) {
        bundle.putSerializable(key, value as java.io.Serializable)
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
    count :Int,
    onIcr :()->Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ExcursionSearchScreen) {
        val onNavigateToExcursion = { it: Excursion -> navController.navigateToExcursionDetail(excursion = it) }
        val onNavigateToAlbum = { excursionId: Int -> navController.navigateToAlbum(excursionId = excursionId) }
        val onNavigateToImage = { imageId: Int,excursionImages: List<Image>,indexImage:Int -> navController.navigateToImage(imageId = imageId,excursionImages=excursionImages,
            indexImage=indexImage) }
        excursionsDestination(snackbarHostState,onNavigateToExcursion,onNavigateToAlbum,onNavigateToImage,count,onIcr)
    }
}

fun NavGraphBuilder.excursionsDestination(snackbarHostState :SnackbarHostState,
                                          onNavigateToExcursion: (Excursion) -> Unit,
                                          onNavigateToAlbum: (Int) -> Unit,
                                          onNavigateToImage: (Int,List<Image>,Int) -> Unit,
                                          count: Int, onIcr:() -> Unit) {
    composable<ExcursionSearchScreen> {
        HomeScreen(snackbarHostState,onNavigateToExcursion)
    }
    composable<ExcursionDetail>(typeMap = ExcursionDetail.typeMap) {
        ExcursionDetailScreen(onNavigateToAlbum,onNavigateToImage,count, onIcr)
    }
    composable<AlbumExcursion> {
        backStackEntry ->
        val excursion = backStackEntry.toRoute<AlbumExcursion>()
        AlbumExcursionScreen(excursionId = excursion.excursionId,navigateToImage=onNavigateToImage)
    }
    composable<ImageExcursion>(
    //    typeMap = mapOf(typeOf<List<Image>>() to NavigationHelpers.parcelableListType<Image>())
        typeMap = mapOf(typeOf<List<Image>>() to ImageNavType)
    ) {
       // ImageExcursionScreen()
        backStackEntry ->
        val data: ImageExcursion = backStackEntry.toRoute()
        ImageExcursionScreen(imageId = data.imageId,excursionImages =data.excursionImages,indexImage = data.indexImage)
    }
}

fun NavController.navigateToExcursionDetail(excursion: Excursion) {
    navigate(route = ExcursionDetail(excursion))
}

fun NavController.navigateToAlbum(excursionId: Int) {
    navigate(route = AlbumExcursion(excursionId))
}

fun NavController.navigateToImage(imageId: Int,excursionImages: List<Image>,indexImage:Int) {
    navigate(route = ImageExcursion(imageId,excursionImages,indexImage))
}