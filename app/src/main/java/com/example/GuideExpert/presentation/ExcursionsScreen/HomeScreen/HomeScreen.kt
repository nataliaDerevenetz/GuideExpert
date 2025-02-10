package com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.GuideExpert.R
import com.example.GuideExpert.data.DataProvider
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.ColumnExcursionShimmer
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.ExcursionListFilterItem
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.ExcursionListSearchItem
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.FilterBar
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.FilterScreen
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.ImageSlider
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.LoadingExcursionListShimmer
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.MainTopBar
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.shimmerEffect
import com.example.GuideExpert.utils.Constant.STATUSBAR_HEIGHT
import kotlin.math.roundToInt

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    snackbarHostState: SnackbarHostState,
    navigateToExcursion: (Excursion) -> Unit,
    viewModel: ExcursionsViewModel = hiltViewModel()
) {

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

            HomeScreenContent(
                snackbarHostState = snackbarHostState,
                onSetFavoriteExcursionButtonClick = {
                    viewModel.handleEvent(
                        ExcursionsUiEvent.OnClickFavoriteExcursion(it)
                    )
                },
                navigateToExcursion = navigateToExcursion,
                toolbarHeightDp = toolbarHeightDp,
                filterScreenVisible = filtersVisible,
                onFiltersSelected = {
                    filtersVisible = true
                },
            )


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

            if (filtersVisible) { scrolling = false}

            AnimatedVisibility(filtersVisible, enter = fadeIn(), exit = fadeOut()) {
                FilterScreen {
                    filtersVisible = false
                    scrolling = true
                }
            }
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

context(SharedTransitionScope)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    snackbarHostState:SnackbarHostState,
    onSetFavoriteExcursionButtonClick: (Excursion) -> Unit,
    navigateToExcursion: (Excursion) -> Unit,
    toolbarHeightDp: Int,
    filterScreenVisible: Boolean,
    onFiltersSelected: () -> Unit,
    viewModel: ExcursionsViewModel = hiltViewModel(), //delete
) {

    val effectFlow by viewModel.effectFlow.collectAsStateWithLifecycle(null)

    LaunchedEffect(snackbarHostState) {
        effectFlow?.let {
            when (it) {
                is SnackbarEffect.ShowSnackbar -> snackbarHostState.showSnackbar(it.message)
            }
        }
    }

    val filters = viewModel.getFiltersBar()
    val excursionPagingItems by rememberUpdatedState(newValue = viewModel.uiPagingState.collectAsLazyPagingItems()) //delete

    if (excursionPagingItems.loadState.refresh is LoadState.Error) {
        LaunchedEffect(key1 = snackbarHostState) {
            viewModel.sendEffectFlow((excursionPagingItems.loadState.refresh as LoadState.Error).error.message ?: "",null)
            Log.d("TAG", excursionPagingItems.loadState.refresh.toString())
        }
    }

    if (excursionPagingItems.loadState.append is LoadState.Error) {
        LaunchedEffect(key1 = snackbarHostState) {
            viewModel.sendEffectFlow((excursionPagingItems.loadState.append as LoadState.Error).error.message ?: "",null)
            Log.d("TAG", excursionPagingItems.loadState.append.toString())
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(top = toolbarHeightDp.dp)
    ) {
        item{
            ImageSlider()
        }
        item {
            FilterBar(
                filters,
                filterScreenVisible = filterScreenVisible,
                onShowFilters = onFiltersSelected,
            )
        }


        if (excursionPagingItems.loadState.refresh is LoadState.Loading) {
            items(20) {
                ColumnExcursionShimmer()
            }
        } else {
            items(
                count = excursionPagingItems.itemCount,
                key = excursionPagingItems.itemKey { it.id },
            ) { index ->
                val excursion = excursionPagingItems[index]
                if (excursion != null) {
                    ExcursionListFilterItem(
                        excursion,
                        onSetFavoriteExcursionButtonClick,
                        navigateToExcursion
                    )
                }
            }
            item {
                if (excursionPagingItems.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
            }
        }

    }
}