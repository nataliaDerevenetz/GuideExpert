package com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.GuideExpert.R
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.ExcursionListSearchItem
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.LoadingExcursionListShimmer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction2

@Stable
class SearchScreenState(
    val searchListState: StateFlow<PagingData<Excursion>>,
    val stateView: StateFlow<ExcursionsSearchUIState>,
    val effectFlow: Flow<SnackbarEffect>,
    val snackbarHostState: SnackbarHostState,
    val onEvent : (SearchEvent) -> Unit,
    val sendEffectFlow : KSuspendFunction2<String, String?, Unit>,
    val navigateToExcursion : (Excursion) -> Unit,
)

@Composable
fun rememberSearchScreenState(
    searchListState: StateFlow<PagingData<Excursion>>,
    stateView: StateFlow<ExcursionsSearchUIState>,
    effectFlow: Flow<SnackbarEffect>,
    snackbarHostState: SnackbarHostState,
    onEvent: (SearchEvent) -> Unit,
    sendEffectFlow: KSuspendFunction2<String, String?, Unit>,
    navigateToExcursion : (Excursion) -> Unit,
): SearchScreenState = remember(searchListState,snackbarHostState,onEvent,sendEffectFlow,navigateToExcursion) {
    SearchScreenState(searchListState,stateView,effectFlow,snackbarHostState,onEvent,sendEffectFlow,navigateToExcursion)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExcursionListSearchScreen(modifier: Modifier = Modifier,
                        snackbarHostState: SnackbarHostState,
                        viewModel: ExcursionSearchViewModel = hiltViewModel(),
                        navigateToExcursion: (Excursion) -> Unit,
                        state: SearchScreenState = rememberSearchScreenState(
                            searchListState = viewModel.uiPagingState,
                            stateView = viewModel.stateView,
                            effectFlow = viewModel.effectFlow,
                            snackbarHostState = snackbarHostState,
                            onEvent = viewModel::onEvent,
                            sendEffectFlow = viewModel::sendEffectFlow,
                            navigateToExcursion = navigateToExcursion,
                        )
){

    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val excursions by rememberUpdatedState(newValue = state.searchListState.collectAsLazyPagingItems())
    val uiState by state.stateView.collectAsStateWithLifecycle()


    LaunchedEffect(state.snackbarHostState) {
        state.effectFlow.collect { effect ->
            when (effect) {
                is SnackbarEffect.ShowSnackbar -> state.snackbarHostState.showSnackbar(effect.message)
            }
        }
    }


    Column {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                query = text,
                onQueryChange = {
                    text = it
                    state.onEvent(SearchEvent.SetSearchText(text))
                },
                onSearch = { expanded = true},
                expanded = expanded,
                onExpandedChange = {
                    if(expanded != it)   state.onEvent(SearchEvent.SetStateListSearch(ExcursionListSearchUIState.Idle))
                    expanded = it
                },
                placeholder = { Text(text = stringResource(R.string.search_str)) },
                leadingIcon = {
                    if (expanded){
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            Modifier.clickable {
                                expanded = false
                                text = ""
                                state.onEvent(SearchEvent.SetSearchText(text))
                                state.onEvent(SearchEvent.SetStateListSearch(ExcursionListSearchUIState.Idle))
                            })
                    } else {
                        Icon(Icons.Default.Search, contentDescription = null)
                    } },
                trailingIcon = {
                    if (expanded){
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = null,
                            Modifier.clickable {
                                state.onEvent(SearchEvent.SetStateListSearch(ExcursionListSearchUIState.Idle))
                                if (text.isNotEmpty()) {
                                    text = ""
                                    state.onEvent(SearchEvent.SetSearchText(text))
                                } else {
                                    expanded = false
                                }
                            }
                        )
                    }
                }
            )},
            expanded = expanded,
            onExpandedChange = { expanded = it
                text = ""
                state.onEvent(SearchEvent.SetSearchText(text)) },
            modifier = Modifier.fillMaxWidth()
        ) {

            when(uiState.contentState){
                is ExcursionListSearchUIState.Idle -> {
                    SearchScreenEmpty()
                }
                is ExcursionListSearchUIState.Loading -> {
                    SearchScreenEmpty()
                }
                is ExcursionListSearchUIState.Data -> {
                    SearchResult(excursions, state)
                }
                is ExcursionListSearchUIState.Error -> {

                }
            }

        }

    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResult(excursionPagingItems: LazyPagingItems<Excursion>, state: SearchScreenState) {
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    if (excursionPagingItems.loadState.refresh is LoadState.Error) {
        LaunchedEffect(key1 = state.snackbarHostState) {
            state.sendEffectFlow((excursionPagingItems.loadState.refresh as LoadState.Error).error.message ?: "",null)
            Log.d("TAG", excursionPagingItems.loadState.refresh.toString())
        }
    }

    if (excursionPagingItems.loadState.append is LoadState.Error) {
        LaunchedEffect(key1 = state.snackbarHostState) {
            state.sendEffectFlow((excursionPagingItems.loadState.append as LoadState.Error).error.message ?: "",null)
            Log.d("TAG", excursionPagingItems.loadState.append.toString())
        }
    }

    Log.d("TAG", "itemCount  = ${excursionPagingItems.itemCount.toString()}")

    Box(modifier = Modifier.padding(top = 8.dp).fillMaxSize()) {
        if (excursionPagingItems.loadState.refresh is LoadState.Loading) {
            LoadingExcursionListShimmer()
        } else {

            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = {
                    coroutineScope.launch {
                        isRefreshing = true
                        excursionPagingItems.refresh()
                        isRefreshing = false
                    }
                },
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(
                        count = excursionPagingItems.itemCount,
                        key = excursionPagingItems.itemKey { it.id },
                    ) { index ->
                        val excursion = excursionPagingItems[index]
                        if (excursion != null) {
                           ExcursionListSearchItem(excursion,state.onEvent,state.navigateToExcursion)
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
    }

}

@Composable
private fun SearchScreenEmpty(modifier: Modifier = Modifier) {
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
