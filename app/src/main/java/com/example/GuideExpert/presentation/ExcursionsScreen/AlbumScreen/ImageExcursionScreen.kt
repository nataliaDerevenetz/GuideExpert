package com.example.GuideExpert.presentation.ExcursionsScreen.AlbumScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.example.GuideExpert.domain.models.Image
import kotlinx.coroutines.flow.Flow


@Composable
fun ImageExcursionScreen(
    imageId: Int,
    viewModel: AlbumExcursionViewModel = hiltViewModel(),
    image: Flow<Image> = viewModel.getImage(imageId)
) {
    val excursionImage by image.collectAsStateWithLifecycle(null)

    var scale by remember { mutableStateOf(1f) }


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
    }
}