package com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

@Composable
fun ExcursionHomeScreen(
    snackbarHostState: SnackbarHostState,
    navigateToExcursion: (Excursion) -> Unit,
    viewModel: ExcursionsViewModel = hiltViewModel()
) {

    val uiState by viewModel.viewState.collectAsStateWithLifecycle()
    val effectFlow by viewModel.effectFlow.collectAsStateWithLifecycle(null)

    val scrollState = rememberScrollState()

    effectFlow?.let {
        //(effectFlow as SnackbarEffect.ShowSnackbar).message
        // send to snackbar  --> message
    }
   // Modifier.verticalScroll(scrollState)
    Column() {

        ExcursionListSearchScreen(
            modifier = Modifier.fillMaxSize(),
            snackbarHostState = snackbarHostState
        )

        Spacer(modifier = Modifier.width(8.dp))

        when (uiState.content) {

            is HomeScreenUiState.Empty -> HomeScreenEmpty()

            is HomeScreenUiState.Content ->
                HomeScreenContent(
                    excursions = (uiState.content as HomeScreenUiState.Content).excursions,
                    onSetFavoriteExcursionButtonClick = {
                        viewModel.handleEvent(
                            ExcursionsUiEvent.OnClickFavoriteExcursion(
                                it
                            )
                        )
                    },
                    navigateToExcursion = navigateToExcursion
                )

            is HomeScreenUiState.Loading -> {}

            is HomeScreenUiState.Error -> {}
        }

    }
}

@Composable
private fun HomeScreenEmpty(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.no_excursions_found),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 27.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    excursions: List<Excursion>,
    onSetFavoriteExcursionButtonClick: (Excursion) -> Unit,
    navigateToExcursion: (Excursion) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(excursions, key = { it.id }) {
            ExcursionListItem(it,onSetFavoriteExcursionButtonClick,navigateToExcursion)
        }
    }
}