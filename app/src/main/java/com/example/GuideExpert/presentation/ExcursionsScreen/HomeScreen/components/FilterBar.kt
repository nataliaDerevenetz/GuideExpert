package com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.border
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.GuideExpert.ui.theme.Shadow1
import com.example.GuideExpert.ui.theme.Shadow2


fun Modifier.diagonalGradientBorder(
    colors: List<Color>,
    borderSize: Dp = 2.dp,
    shape: Shape
) = border(
    width = borderSize,
    brush = Brush.linearGradient(colors),
    shape = shape
)

object FilterSharedElementKey

context(SharedTransitionScope)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FilterBar(
    filters: List<String>,
    onShowFilters: () -> Unit,
    filterScreenVisible: Boolean,
   // sharedTransitionScope: SharedTransitionScope
) {

    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 12.dp, end = 8.dp),
        modifier = Modifier.heightIn(min = 56.dp)
    ) {
        item {
            AnimatedVisibility(visible = !filterScreenVisible) {
                IconButton(
                    onClick = onShowFilters,
                    modifier = Modifier
                        .sharedBounds(
                            rememberSharedContentState(FilterSharedElementKey),
                            animatedVisibilityScope = this@AnimatedVisibility,
                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds
                        )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FilterList,
                        tint = Shadow1,
                        contentDescription = "",
                        modifier = Modifier.diagonalGradientBorder(
                            colors = listOf(Shadow1, Shadow2),
                            shape = CircleShape
                        )
                    )
                }
            }
        }
        items(filters) { filter ->
            Text(filter)
         //   FilterChip(filter = filter, shape = MaterialTheme.shapes.small)
        }
    }

}