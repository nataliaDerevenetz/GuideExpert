package com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.GuideExpert.R
import com.example.GuideExpert.data.DataProvider
import com.example.GuideExpert.domain.models.Filter
import com.example.GuideExpert.domain.models.FilterType
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.ExcursionsViewModel
import com.example.GuideExpert.ui.theme.Shadow1
import com.example.GuideExpert.ui.theme.Shadow2

context(SharedTransitionScope, AnimatedVisibilityScope)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FilterScreen(
    viewModel: ExcursionsViewModel = hiltViewModel(),
    onDismiss: () -> Unit
) {
    val sortState = viewModel.sortState.collectAsState()
   // var sortState by remember { mutableStateOf(DataProvider.sortDefault) }

    Log.d("TAG","SORT ID :: ${sortState.value.toString()}")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                // capture click
            }
    ) {

        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onDismiss()
                }
        )

        Column(
            Modifier
                .padding(16.dp)
                .align(Alignment.Center)
                .clip(MaterialTheme.shapes.medium)
                .sharedBounds(
                    rememberSharedContentState(FilterSharedElementKey),
                    animatedVisibilityScope = this@AnimatedVisibilityScope,
                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                    clipInOverlayDuringTransition = OverlayClip(MaterialTheme.shapes.medium)
                )
                .wrapContentSize()
                .heightIn(max = 450.dp)
                .verticalScroll(rememberScrollState())
                /*.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { }*/
                .background(MaterialTheme.colorScheme.onSecondary)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .skipToLookaheadSize(),
        ) {
            Row(modifier = Modifier.height(IntrinsicSize.Min).fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = ""
                    )
                }
                Text(
                    text = stringResource(id = R.string.label_filters),
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 8.dp, end = 48.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
                val resetEnabled = true//sortState != defaultFilter

                IconButton(
                    onClick = { /* TODO: Open search */ },
                    enabled = resetEnabled
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "",
                        tint = Shadow1.copy(alpha = if (!resetEnabled) 0.38f else 1f)
                    )

                }

            }

            SortFiltersSection(
                sortState = sortState.value,
                onFilterChange = { filter ->
                    viewModel.setSortState(filter.id)
                }
            )

            FilterChipSection(
                title = stringResource(id = R.string.categories),
                filters = DataProvider.filtersCategories
            )

            FilterChipSection(
                title = stringResource(id = R.string.groups),
                filters = DataProvider.filtersGroups
            )

            FilterChipSection(
                title = stringResource(id = R.string.duration),
                filters = DataProvider.filtersDuration
            )
        }
    }
}

@Composable
fun SortFiltersSection(sortState: Int, onFilterChange: (Filter) -> Unit) {
    FilterTitle(text = stringResource(id = R.string.sort))
    Column(Modifier.padding(bottom = 24.dp)) {
        SortFilters(
            sortState = sortState,
            onChanged = onFilterChange
        )
    }
}

@Composable
fun FilterTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = Shadow1,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun SortFilters(
    sortFilters: List<Filter> = DataProvider.filtersSort,
    filtersBar: List<Filter> = DataProvider.filtersBar,
        sortState: Int,
    onChanged: (Filter) -> Unit
) {

    filtersBar.filter{  it.type == FilterType.Sort }.map{
            val (selected, setSelected) = it.enabled
            setSelected(false)
    }

    sortFilters.forEach { filter ->
        SortOption(
            text = filter.name,
            icon = filter.icon,
            selected = sortState == filter.id,
            onClickOption = {
                onChanged(filter)
            }
        )
    }
}

@Composable
fun SortOption(
    text: String,
    icon: ImageVector?,
    onClickOption: () -> Unit,
    selected: Boolean
) {
    Row(
        modifier = Modifier
            .padding(top = 14.dp)
            .selectable(selected) { onClickOption() }
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null)
        }
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f)
        )
        if (selected) {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = null,
                tint = Shadow1
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterChipSection(title: String, filters: List<Filter>) {
    FilterTitle(text = title)
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 16.dp)
            .padding(horizontal = 4.dp)
    ) {
        filters.forEach { filter ->
            FilterChip(
                filter = filter,
                modifier = Modifier.padding(end = 4.dp, bottom = 8.dp),
                shape = CircleShape
            )
        }
    }
}
