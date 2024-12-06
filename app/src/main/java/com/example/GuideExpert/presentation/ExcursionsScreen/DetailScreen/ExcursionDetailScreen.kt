package com.example.GuideExpert.presentation.ExcursionsScreen.DetailScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

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