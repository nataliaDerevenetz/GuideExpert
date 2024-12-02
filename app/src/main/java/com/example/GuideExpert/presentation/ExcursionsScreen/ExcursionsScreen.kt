package com.example.GuideExpert.presentation.ExcursionsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.GuideExpert.domain.models.Excursion


@Composable
fun ExcursionsScreen(
    count :Int,
    onIcr :()->Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ExcursionSearchScreen) {
        val onNavigateToExcursion = { it: Excursion -> navController.navigateToExcursionDetail(excursion = it) }
        excursionsDestination(onNavigateToExcursion,count,onIcr)
    }

}


@Composable
fun ExcursionDetailScreen(
    count :Int,
    onIcr :()->Unit,
    viewModel: ExcursionDetailViewModel = hiltViewModel()
) {
    val excursion by viewModel.excursion.collectAsStateWithLifecycle()
    val excursionData by viewModel.excursionData.observeAsState()
    Column {
        Text("ExcursionDetailScreen")
        Text("id ${excursion.id}")
        Text("Excursion ${excursionData?.title}")
        Text("Excursion ${excursionData?.company}")
        Text("Excursion ${excursionData?.excursionId}")

        Column {
            Text("Incr :: $count")
            Button(onClick = {onIcr()}) {
                Text(text = "Increase", fontSize = 25.sp)
            }
        }
    }
    // TODO("Not yet implemented")
}


@Composable
fun ExcursionSearchScreen(
    navigateToExcursion: (Excursion) -> Unit,
    viewModel: ExcursionsViewModel = hiltViewModel()
    ) {

   // val excursions by viewModel.allExcursion.collectAsStateWithLifecycle(listOf())
    val excursions by viewModel.allExcursionState.collectAsStateWithLifecycle(listOf())

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(excursions, key = { it.id }) {
            ExcursionListItem(it,navigateToExcursion)
        }
    }
}
