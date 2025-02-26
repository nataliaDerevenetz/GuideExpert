package com.example.GuideExpert.presentation.ExcursionsScreen.AlbumScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.Image
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.NetworkImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.Flow
import kotlin.math.absoluteValue


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageExcursionScreen(
    imageId: Int,
    excursionImages: List<Image>,
    indexImage:Int,
    viewModel: AlbumExcursionViewModel = hiltViewModel(),
    image: Flow<Image> = viewModel.getImage(imageId)
) {
   // val excursionImage by image.collectAsStateWithLifecycle(null)

    var scale by remember { mutableStateOf(1f) }

    val pagerState = rememberPagerState(initialPage = indexImage)

    excursionImages?.let {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    scale *= zoom
                }
            }
        ) {
            HorizontalPager(
                count = excursionImages.size,
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
                        url = excursionImages[page].url,//imageSlider[page],
                        width = 350,
                        height = 450
                    )

            }
        }
    }

/*
    excursionImage?.let {
        Box(contentAlignment = Alignment.Center,modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
            detectTransformGestures { centroid, pan, zoom, rotation ->
                scale *= zoom
            }
        }
            ) {
            SubcomposeAsyncImage(
                model = it.url,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(300.dp)
                    .graphicsLayer(
                        scaleX = maxOf(1f, minOf(3f, scale)),
                        scaleY = maxOf(1f, minOf(3f, scale)),
                    ),
                loading = {
                    CircularProgressIndicator(
                        color = Color.Gray,
                        modifier = Modifier.requiredSize(48.dp)
                    )
                },
                error = {
                    Log.d("TAG", it.toString())
                    Log.d("TAG", "image load: Error!")
                    Log.d("TAG", "something went wrong ${it.result.throwable.localizedMessage}")
                }
            )
        }
    }*/
}