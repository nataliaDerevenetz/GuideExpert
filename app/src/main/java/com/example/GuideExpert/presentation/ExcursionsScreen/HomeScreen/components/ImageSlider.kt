package com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(modifier: Modifier = Modifier,
){

    val pagerState = rememberPagerState(initialPage = 0)

    val imageSlider = listOf("https://media.npr.org/assets/img/2021/08/11/gettyimages-1279899488_wide-f3860ceb0ef19643c335cb34df3fa1de166e2761-s1100-c50.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRrfPnodZbEjtJgE-67C-0W9pPXK8G9Ai6_Rw&usqp=CAU",
        "https://i.ytimg.com/vi/E9iP8jdtYZ0/maxresdefault.jpg",
        "https://cdn.shopify.com/s/files/1/0535/2738/0144/articles/shutterstock_149121098_360x.jpg")


    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2600)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }

    Box(Modifier.padding(top=10.dp)) {
        HorizontalPager(
            count = imageSlider.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = modifier
                .height(150.dp)
                .fillMaxWidth()
        ) { page ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                NetworkImage(
                    contentDescription = "",
                    url = imageSlider[page],
                    width = 350,
                    height = 450
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }

}

@Composable
fun NetworkImage(url: String, contentDescription: String?, width: Int, height: Int) {
    val painter: Painter = rememberAsyncImagePainter(url)
    Image(
        painter = painter,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
    )
}