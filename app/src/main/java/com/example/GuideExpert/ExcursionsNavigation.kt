package com.example.GuideExpert

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.GuideExpert.data.Excursion
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


fun NavGraphBuilder.excursionsDestination(onNavigateToExcursion: (Excursion) -> Unit, count: Int, onIcr:() -> Unit) {
    composable<ExcursionSearchScreen> {
        ExcursionSearchScreen(onNavigateToExcursion)
    }
    composable<ExcursionDetail>(typeMap = ExcursionDetail.typeMap) {
        ExcursionDetailScreen(count, onIcr)
    }
}

fun NavController.navigateToExcursionDetail(excursion: Excursion) {
    navigate(route = ExcursionDetail(excursion))
}