package com.example.GuideExpert.presentation.ExcursionsScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.GuideExpert.R
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.presentation.ExcursionsScreen.DetailScreen.ExcursionDetailViewModel
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.ExcursionListItem
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.ExcursionsViewModel
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.HomeScreenUiState


@Composable
fun ExcursionsScreen(
    count :Int,
    onIcr :()->Unit
) {
    NavigationHomeScreen(count,onIcr)
}
