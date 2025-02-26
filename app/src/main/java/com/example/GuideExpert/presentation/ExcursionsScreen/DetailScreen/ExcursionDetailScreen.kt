package com.example.GuideExpert.presentation.ExcursionsScreen.DetailScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.example.GuideExpert.R
import com.example.GuideExpert.domain.models.Image

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExcursionDetailScreen(
    navigateToAlbum: (Int) -> Unit,
    navigateToImage: (Int,List<Image>,Int) -> Unit,
    count :Int,
    onIcr :()->Unit,
    viewModel: ExcursionDetailViewModel = hiltViewModel()
) {

    val excursionData by viewModel.excursion.collectAsStateWithLifecycle(null)

    val excursionImages by viewModel.images.collectAsStateWithLifecycle(null)

    Column(Modifier.padding(start=10.dp, end=10.dp)) {

        excursionImages?.let {
            HorizontalMultiBrowseCarousel(
                state = rememberCarouselState { excursionImages!!.count() },
                modifier = Modifier.fillMaxWidth().height(250.dp),
                preferredItemWidth = 350.dp,
                itemSpacing = 1.dp,
                contentPadding = PaddingValues(horizontal = 0.dp)
            ) { i ->
                val item = it[i]
                Card(
                    modifier = Modifier.height(250.dp).maskClip(MaterialTheme.shapes.extraLarge)
                ) {
                    NetworkImageCarousel(item.url, "", 500, 250,navigateToImage,item.id,excursionImages!!,i )
                }
            }
        }

        TextButton(modifier = Modifier.align(Alignment.End), onClick = {excursionData?.let { navigateToAlbum(it.excursionId)}}) {
            Text(stringResource(id = R.string.showall),color= Color.Blue)
        }

        Text("ExcursionDetailScreen")
        Text("id ${excursionData?.excursionId}")
        Text("Excursion ${excursionData?.title}")
        Text("Excursion ${excursionData?.description}")
        Text("Excursion ${excursionData?.excursionId}")

        Column {
            Text("Incr :: $count")
            Button(onClick = {onIcr()}) {
                Text(text = "Increase", fontSize = 25.sp)
            }
        }
    }
}

@Composable
fun NetworkImageCarousel(url: String, contentDescription: String?, width: Int, height: Int,
                         navigateToImage: (Int,List<Image>,Int) -> Unit, imageId:Int,
                         excursionImages: List<Image>,
                         indexImage:Int) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth().height(height.dp).clickable { navigateToImage(imageId,excursionImages,indexImage) },
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