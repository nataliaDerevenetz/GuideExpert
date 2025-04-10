package com.example.GuideExpert.presentation.FavoriteScreen.FavoriteMainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.GuideExpert.R
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.SnackbarEffect
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.LoadingExcursionListShimmer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Stable
interface FavoritesScope {
    val handleEvent : (ExcursionsFavoriteUiEvent) -> Unit
    val stateLoadFavorites: StateFlow<LoadFavoritesUIState>
    val effectFlow: Flow<SnackbarEffect>
    val snackbarHostState: SnackbarHostState
    val excursions: Flow<List<Excursion>>
}

fun DefaultFavoritesScope(
    handleEvent : (ExcursionsFavoriteUiEvent) -> Unit,
    stateLoadFavorites:  StateFlow<LoadFavoritesUIState>,
    effectFlow: Flow<SnackbarEffect>,
    snackbarHostState: SnackbarHostState,
    excursions: Flow<List<Excursion>>
): FavoritesScope {
    return object : FavoritesScope {
        override val handleEvent: (ExcursionsFavoriteUiEvent) -> Unit
            get() = handleEvent
        override val stateLoadFavorites: StateFlow<LoadFavoritesUIState>
            get() = stateLoadFavorites
        override val effectFlow: Flow<SnackbarEffect>
            get() = effectFlow
        override val snackbarHostState:SnackbarHostState
            get() = snackbarHostState
        override val excursions:Flow<List<Excursion>>
            get() = excursions
    }
}

@Composable
fun rememberDefaultFavoritesScope(
    handleEvent : (ExcursionsFavoriteUiEvent) -> Unit,
    stateLoadFavorites:  StateFlow<LoadFavoritesUIState>,
    effectFlow: Flow<SnackbarEffect>,
    snackbarHostState: SnackbarHostState,
    excursions: Flow<List<Excursion>>
): FavoritesScope = remember(handleEvent,stateLoadFavorites,effectFlow,snackbarHostState,excursions) {
    DefaultFavoritesScope(handleEvent,stateLoadFavorites,effectFlow,snackbarHostState,excursions)
}


@Composable
fun Favorites(snackbarHostState: SnackbarHostState,
              viewModel: FavoritesViewModel = hiltViewModel(),
              scopeState:FavoritesScope = rememberDefaultFavoritesScope(
                  handleEvent = viewModel::handleEvent,
                  stateLoadFavorites = viewModel.stateLoadFavorites,
                  effectFlow = viewModel.effectFlow,
                  snackbarHostState = snackbarHostState,
                  excursions = viewModel.excursions
              )
)
{
    val stateLoadFavorites by viewModel.stateLoadFavorites.collectAsStateWithLifecycle()
    val effectFlow by scopeState.effectFlow.collectAsStateWithLifecycle(null)

    when(stateLoadFavorites.contentState){
        is LoadFavoritesState.Success -> { scopeState.FavoritesDataContent() }
        is LoadFavoritesState.Error -> { scopeState.FavoritesDataError(effectFlow) }
        is LoadFavoritesState.Idle -> {}
        is LoadFavoritesState.Loading -> { LoadingExcursionListShimmer() }
    }
}

@Composable
fun FavoritesScope.FavoritesDataError(effectFlow: SnackbarEffect?) {
    val scope = rememberCoroutineScope()
    effectFlow?.let {
        when (it) {
            is SnackbarEffect.ShowSnackbar -> {
                scope.launch {
                    snackbarHostState.showSnackbar(it.message)
                }
            }
        }
    }
    handleEvent(ExcursionsFavoriteUiEvent.OnLoadFavoritesUIStateSetIdle)

    Column (Modifier.padding(0.dp).fillMaxSize()){
        Row(
            Modifier.padding(start = 15.dp, end = 15.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(id = R.string.failedload), color = Color.Gray)
            TextButton({
                handleEvent(ExcursionsFavoriteUiEvent.OnLoadExcursionsFavorite)
            }) {
                Text(stringResource(id = R.string.update), fontSize = 15.sp, color = Color.Blue.copy(alpha =.5f))
            }
        }
        Row {
            FavoritesDataContent()
        }
    }
}

@Composable
fun FavoritesScope.FavoritesDataContent() {
    val excursions by excursions.collectAsStateWithLifecycle(null)
    val scope = rememberCoroutineScope()

    excursions?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 12.dp),
        ) {
            itemsIndexed(
                items = it,
                key = { _, item -> item.id }
            ) { _, excursion ->
                ExcursionFavoriteItem(excursion,scope)
            }
        }
    }

}

