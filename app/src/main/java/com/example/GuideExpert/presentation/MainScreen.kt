package com.example.GuideExpert.presentation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.GuideExpert.presentation.ExcursionsScreen.ExcursionsScreen
import com.example.GuideExpert.presentation.ProfileScreen.ProfileScreen
import com.example.GuideExpert.ui.theme.Purple80
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
    var bottomBarState by rememberSaveable { (mutableStateOf(true)) }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomBar(navController,bottomBarState)
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Home, Modifier.padding(innerPadding)) {
            composable<Home> { ExcursionsScreen(snackbarHostState = snackbarHostState,
                onChangeVisibleBottomBar = {visibleBottomBar:Boolean -> bottomBarState = visibleBottomBar},
                count = viewModel.count, onIcr = {viewModel.increase()}) }
            composable<Profile> { ProfileScreen(viewModel.count,onIcr = {viewModel.increase()}) }
        }
    }
}

@Composable
fun BottomBar(navController: NavController, bottomBarState: Boolean) {
    AnimatedVisibility(
        visible = bottomBarState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation(backgroundColor = Purple80){
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                topLevelRoutes.forEach { topLevelRoute ->
                    val selected = currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true
                    Log.d("TAG",selected.toString())
                    NavigationBarItem(
                        selected = selected,
                        label = { Text(topLevelRoute.title,
                            color = Color.Black
                        ) },

                        icon = {Icon(
                            imageVector = if (selected) {
                                topLevelRoute.selectedIcon
                            } else topLevelRoute.unselectedIcon,
                            contentDescription = topLevelRoute.title,
                            tint = if (selected) {MaterialTheme.colorScheme.inverseSurface} else Color.Black
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
    )
}
