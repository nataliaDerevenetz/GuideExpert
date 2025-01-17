package com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.GuideExpert.R
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.ExcursionListFilterItem
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.ImageSlider
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.MainTopBar
import com.example.GuideExpert.utils.Constant.STATUSBAR_HEIGHT
import kotlin.math.roundToInt

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    snackbarHostState: SnackbarHostState,
    navigateToExcursion: (Excursion) -> Unit,
    viewModel: ExcursionsViewModel = hiltViewModel()
) {

    val uiState by viewModel.viewState.collectAsStateWithLifecycle()
    val effectFlow by viewModel.effectFlow.collectAsStateWithLifecycle(null)

    effectFlow?.let {
        //(effectFlow as SnackbarEffect.ShowSnackbar).message
        // send to snackbar  --> message
    }

    val screenHeightDp =  LocalConfiguration.current.screenHeightDp

    var toolbarHeight by rememberSaveable { mutableStateOf(screenHeightDp) }
    var toolbarHeightDp by rememberSaveable { mutableStateOf(toolbarHeight) }
    var toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.dp.roundToPx().toFloat() }


    val localDensity = LocalDensity.current
    var scrolling by rememberSaveable { mutableStateOf(true) }
    var toolbarOffsetHeightPx by rememberSaveable { mutableStateOf(0f) }
    val statusbarHeight =  with(localDensity) { STATUSBAR_HEIGHT.dp.roundToPx().toFloat() }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx + delta
                if (scrolling) toolbarOffsetHeightPx = newOffset.coerceIn(-(toolbarHeightPx+statusbarHeight), 0f)
                return Offset.Zero
            }
        }
    }

    var filtersVisible by remember {
        mutableStateOf(false)
    }

    SharedTransitionLayout {
        Box(
            Modifier.fillMaxSize().nestedScroll(nestedScrollConnection)
        ) {

            when (uiState.content) {

                is HomeScreenUiState.Empty -> HomeScreenEmpty()

                is HomeScreenUiState.Content ->
                    HomeScreenContent(
                        excursions = (uiState.content as HomeScreenUiState.Content).excursions,
                        onSetFavoriteExcursionButtonClick = {
                            viewModel.handleEvent(
                                ExcursionsUiEvent.OnClickFavoriteExcursion(it)
                            )
                        },
                        navigateToExcursion = navigateToExcursion,
                        toolbarHeightDp = toolbarHeightDp
                    )

                is HomeScreenUiState.Loading -> {}

                is HomeScreenUiState.Error -> {}
            }


            MainTopBar(modifier = Modifier.fillMaxSize(),
                snackbarHostState = snackbarHostState,
                navigateToExcursion = navigateToExcursion,
                toolbarHeightDp = toolbarHeightDp,
                toolbarOffsetHeightPx = toolbarOffsetHeightPx,
                scrollingOn = {
                    toolbarOffsetHeightPx = 0f
                    scrolling = false
                    toolbarHeightDp = screenHeightDp
                },
                scrollingOff = {
                    scrolling = true
                    toolbarHeightDp = toolbarHeight
                },
                onToolbarHeightChange = {
                    toolbarHeight = with(localDensity) { it.toDp() }.value.roundToInt()
                    toolbarHeightDp = toolbarHeight
                    toolbarHeightPx = with(localDensity) { toolbarHeight.dp.roundToPx().toFloat() }
                }
            )
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
    toolbarHeightDp: Int
) {
    LazyColumn(
        contentPadding = PaddingValues(top = toolbarHeightDp.dp)
    ) {
        item{
            ImageSlider()
        }
        items(excursions, key = { it.id }) {
            ExcursionListFilterItem(it,onSetFavoriteExcursionButtonClick,navigateToExcursion)
        }
    }
}