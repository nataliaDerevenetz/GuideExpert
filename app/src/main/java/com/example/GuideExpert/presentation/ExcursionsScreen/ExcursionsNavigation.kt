package com.example.GuideExpert.presentation.ExcursionsScreen

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
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.ExcursionHomeScreen
import com.example.GuideExpert.serializableType
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
object ExcursionSearchScreen

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
    count :Int,
    onIcr :()->Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ExcursionSearchScreen) {
        val onNavigateToExcursion = { it: Excursion -> navController.navigateToExcursionDetail(excursion = it) }
        excursionsDestination(onNavigateToExcursion,count,onIcr)
    }
}

fun NavGraphBuilder.excursionsDestination(onNavigateToExcursion: (Excursion) -> Unit, count: Int, onIcr:() -> Unit) {
    composable<ExcursionSearchScreen> {
        ExcursionHomeScreen(onNavigateToExcursion)
    }
    composable<ExcursionDetail>(typeMap = ExcursionDetail.typeMap) {
        ExcursionDetailScreen(count, onIcr)
    }
}

fun NavController.navigateToExcursionDetail(excursion: Excursion) {
    navigate(route = ExcursionDetail(excursion))
}