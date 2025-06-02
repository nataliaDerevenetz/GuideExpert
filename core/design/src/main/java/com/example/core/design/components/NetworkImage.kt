package com.example.core.design.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun NetworkImage(url: String, contentDescription: String?, width: Int, height: Int) {
    SubcomposeAsyncImage(
        model = url/*ImageRequest.Builder(LocalContext.current)
            .data(url)
            .setHeader("User-Agent", "Mozilla/5.0")
            .build()*/,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth().height(height.dp),
        loading = {
            CircularProgressIndicator(
                color = Color.Gray,
                modifier = Modifier.requiredSize(48.dp)
            )
        },
        error = {
            Log.d("TAG", url)
            Log.d("TAG", "image load: Error!")
            Log.d("TAG", "something went wrong ${it.result.throwable.localizedMessage}")
        }
    )
}