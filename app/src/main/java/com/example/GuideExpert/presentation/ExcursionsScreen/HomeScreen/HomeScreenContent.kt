package com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen

import android.util.Log
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.GuideExpert.R
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.Filter
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.ColumnExcursionShimmer
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.ExcursionListFilterItem
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.FilterBar
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.ImageSlider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KFunction1
import kotlin.reflect.KSuspendFunction2


@Stable
class HomeScreenContentState(
    val filterListState: StateFlow<PagingData<Excursion>>,
    val effectFlow: Flow<SnackbarEffect>,
    val snackbarHostState: SnackbarHostState,
    val onEvent : KFunction1<ExcursionsUiEvent, Unit>,
    val sendEffectFlow : KSuspendFunction2<String, String?, Unit>,
    val navigateToExcursion : (Excursion) -> Unit,
    val getFiltersBar:() -> List<Filter>,
)

@Composable
fun rememberHomeScreenContentState(
    filterListState: StateFlow<PagingData<Excursion>>,
    effectFlow: Flow<SnackbarEffect>,
    snackbarHostState: SnackbarHostState,
    onEvent: KFunction1<ExcursionsUiEvent, Unit>,
    sendEffectFlow: KSuspendFunction2<String, String?, Unit>,
    navigateToExcursion: (Excursion) -> Unit,
    getFiltersBar:() -> List<Filter>,
): HomeScreenContentState = remember(filterListState,snackbarHostState,onEvent,sendEffectFlow,navigateToExcursion,getFiltersBar) {
    HomeScreenContentState(filterListState,effectFlow,snackbarHostState,onEvent,sendEffectFlow,navigateToExcursion,getFiltersBar)
}

context(SharedTransitionScope)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    navigateToExcursion: (Excursion) -> Unit,
    toolbarHeightDp: Int,
    filterScreenVisible: Boolean,
    onFiltersSelected: () -> Unit,
    viewModel: ExcursionsViewModel = hiltViewModel(),
    state: HomeScreenContentState = rememberHomeScreenContentState(
        filterListState = viewModel.uiPagingState,
        effectFlow = viewModel.effectFlow,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::handleEvent,
        sendEffectFlow = viewModel::sendEffectFlow,
        navigateToExcursion = navigateToExcursion,
        getFiltersBar = viewModel::getFiltersBar
    )
) {

    val effectFlow by state.effectFlow.collectAsStateWithLifecycle(null)

    LaunchedEffect(state.snackbarHostState) {
        effectFlow?.let {
            when (it) {
                is SnackbarEffect.ShowSnackbar -> snackbarHostState.showSnackbar(it.message)
            }
        }
    }

    val filters = state.getFiltersBar()
    val excursionPagingItems by rememberUpdatedState(newValue = state.filterListState.collectAsLazyPagingItems())

    if (excursionPagingItems.loadState.refresh is LoadState.Error) {
        LaunchedEffect(key1 = snackbarHostState) {
            state.sendEffectFlow((excursionPagingItems.loadState.refresh as LoadState.Error).error.message ?: "",null)
            Log.d("TAG", excursionPagingItems.loadState.refresh.toString())
        }
    }

    if (excursionPagingItems.loadState.append is LoadState.Error) {
        LaunchedEffect(key1 = snackbarHostState) {
            state.sendEffectFlow((excursionPagingItems.loadState.append as LoadState.Error).error.message ?: "",null)
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
            if(excursionPagingItems.loadState.source.refresh is LoadState.NotLoading &&
                excursionPagingItems.loadState.append.endOfPaginationReached)
            {
                item{
                    HomeScreenEmpty()
                }
            }
            items(
                count = excursionPagingItems.itemCount,
                key = excursionPagingItems.itemKey { it.id },
            ) { index ->
                val excursion = excursionPagingItems[index]
                if (excursion != null) {
                    ExcursionListFilterItem(
                        excursion,
                        {state.onEvent(ExcursionsUiEvent.OnClickFavoriteExcursion(excursion))},
                        state.navigateToExcursion
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