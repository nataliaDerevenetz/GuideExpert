package com.example.GuideExpert.presentation.ProfileScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Screen1

@Serializable
data class Screen2(
    var name : String = "test"
)

fun NavGraphBuilder.profileDestination(onNavigateToScreen2: () -> Unit, count: Int, onIcr:() -> Unit) {
    composable<Screen1> { Screen1View(onNavigateToScreen2) }
    composable<Screen2> { Screen2View(count,onIcr) }
}

fun NavController.navigateToScreen2(str: String) {
    navigate(route = Screen2(str))
}

