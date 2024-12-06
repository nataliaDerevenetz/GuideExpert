package com.example.GuideExpert.presentation.ProfileScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
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
fun NavigationProfileScreen(count :Int, onIcr :()->Unit)  {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen1) {
        val onNavigateToScreen2 = { navController.navigateToScreen2("Ok it Screen2")}
        profileDestination(onNavigateToScreen2,count,onIcr)
    }

}

fun NavGraphBuilder.profileDestination(onNavigateToScreen2: () -> Unit, count: Int, onIcr:() -> Unit) {
    composable<Screen1> { Screen1View(onNavigateToScreen2) }
    composable<Screen2> { Screen2View(count,onIcr) }
}

fun NavController.navigateToScreen2(str: String) {
    navigate(route = Screen2(str))
}

