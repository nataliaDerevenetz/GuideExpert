package com.example.GuideExpert.presentation.ExcursionsScreen.AlbumScreen

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.NetworkImage
import com.example.GuideExpert.presentation.ExcursionsScreen.ImageExcursion
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageExcursionScreen(
    imageExcursion: ImageExcursion,
    onChangeVisibleBottomBar: (Boolean) -> Unit,
) {
    var scale by remember { mutableStateOf(1f) }
    val pagerState = rememberPagerState(initialPage = imageExcursion.indexImage)
    onChangeVisibleBottomBar(false)

    imageExcursion.excursionImages?.let {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    scale *= zoom
                }
            }
        ) {
            HorizontalPager(
                count = imageExcursion.excursionImages.size,
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 0.dp),
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .graphicsLayer(
                        scaleX = maxOf(1f, minOf(3f, scale)),
                        scaleY = maxOf(1f, minOf(3f, scale)),
                    )
            ) { page ->
                    NetworkImage(
                        contentDescription = "",
                        url = imageExcursion.excursionImages[page].url,
                        width = 350,
                        height = 450
                    )

            }
        }
    }
}