package com.example.GuideExpert.presentation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.GuideExpert.presentation.ExcursionsScreen.ExcursionsScreen
import com.example.GuideExpert.presentation.ProfileScreen.ProfileScreen
import kotlinx.serialization.Serializable


@Serializable
object Home

@Serializable
object Profile


data class TopLevelRoute<T : Any>(
    val title: String,
    val route: T,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector)

val topLevelRoutes = listOf(
    TopLevelRoute("Home", Home, Icons.Filled.Home,Icons.Outlined.Home),
    TopLevelRoute("Person", Profile, Icons.Filled.Person,Icons.Outlined.Person)
)

@Composable
fun MainScreen(viewModel: UserViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomNavigation{
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                topLevelRoutes.forEach { topLevelRoute ->
                    val selected = currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true
                    Log.d("TAG",selected.toString())
                    NavigationBarItem(
                        selected = selected,
                        label = { Text(topLevelRoute.title) },

                        icon = {Icon(
                                imageVector = if (selected) {
                                    topLevelRoute.selectedIcon
                                } else topLevelRoute.unselectedIcon,
                                contentDescription = topLevelRoute.title,
                                tint = Color.Black
                            )},
                        alwaysShowLabel = false,
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Home, Modifier.padding(innerPadding)) {
            composable<Home> { ExcursionsScreen(snackbarHostState = snackbarHostState, count = viewModel.count, onIcr = {viewModel.increase()}) }
            composable<Profile> { ProfileScreen(viewModel.count,onIcr = {viewModel.increase()}) }
        }
    }
}
