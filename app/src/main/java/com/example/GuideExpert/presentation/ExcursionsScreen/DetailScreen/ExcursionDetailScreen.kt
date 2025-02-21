package com.example.GuideExpert.presentation.ExcursionsScreen.DetailScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ExcursionData

@Composable
fun ExcursionDetailScreen(
    count :Int,
    onIcr :()->Unit,
    viewModel: ExcursionDetailViewModel = hiltViewModel()
) {
  //  val excursion by viewModel.excursion.collectAsStateWithLifecycle()
  //  val excursionData by viewModel.excursionData.observeAsState()

  //  val excursionData2 by viewModel.excursionData2.collectAsStateWithLifecycle(ExcursionData())
    //collectAsStateWithLifecycle(Excursion)

    val excursionData by viewModel.excursion.collectAsStateWithLifecycle(null)

    Column {
        Text("ExcursionDetailScreen")
        Text("id ${excursionData?.excursionId}")
        Text("Excursion ${excursionData?.title}")
        Text("Excursion ${excursionData?.description}")
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