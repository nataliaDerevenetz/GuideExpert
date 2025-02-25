package com.example.GuideExpert.presentation.ExcursionsScreen.DetailScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.domain.models.ExcursionData
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.NetworkImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExcursionDetailScreen(
    count :Int,
    onIcr :()->Unit,
    viewModel: ExcursionDetailViewModel = hiltViewModel()
) {
  //  val excursion by viewModel.excursion.collectAsStateWithLifecycle()
  //  val excursionData by viewModel.excursionData.observeAsState()

  //  val excursionData2 by viewModel.excursionData2.collectAsStateWithLifecycle(ExcursionData())
    //collectAsStateWithLifecycle(Excursion)

    val excursionData by viewModel.excursion.collectAsStateWithLifecycle(null)

    val excursionImages by viewModel.images.collectAsStateWithLifecycle(null)

    Column {

        if (excursionImages !== null) {
            HorizontalMultiBrowseCarousel(
                state = rememberCarouselState { excursionImages!!.count() },
                modifier = Modifier.fillMaxWidth().height(221.dp),
                preferredItemWidth = 350.dp,
                itemSpacing = 1.dp,
                contentPadding = PaddingValues(horizontal = 0.dp)
            ) { i ->
                val item = excursionImages?.get(i)
                Card(
                    modifier = Modifier.height(205.dp).maskClip(MaterialTheme.shapes.extraLarge))
                {
                    NetworkImageCarousel(item!!.url, "", 500, 221)
                }
                /* Image(
                modifier = Modifier.height(205.dp).maskClip(MaterialTheme.shapes.extraLarge),
                painter = painterResource(id = item.imageResId),
                contentDescription = stringResource(item.contentDescriptionResId),
                contentScale = ContentScale.Crop
            )*/
            }
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
    // TODO("Not yet implemented")
}

@Composable
fun NetworkImageCarousel(url: String, contentDescription: String?, width: Int, height: Int) {
    SubcomposeAsyncImage(
        model = url,
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