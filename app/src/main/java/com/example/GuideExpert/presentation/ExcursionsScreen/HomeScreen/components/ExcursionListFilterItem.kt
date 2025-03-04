package com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.GuideExpert.R
import com.example.GuideExpert.domain.models.Excursion
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.ExcursionsUiEvent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@Composable
fun ExcursionListFilterItem(
    excursion: Excursion,
    onEvent: (ExcursionsUiEvent) -> Unit,
    navigateToExcursion: (Excursion) -> Unit,
) {
    Card(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp).fillMaxWidth(),
         colors = CardDefaults.cardColors(
             containerColor = Color.Transparent, //Card background color
           //  contentColor = Color.DarkGray  //Card content color,e.g.text
         ),
        shape = RoundedCornerShape(15.dp),
      //  elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ){

        //  Log.e("TEST","row")
        Row( modifier = Modifier.clickable{
            Log.d("TAG", "clickable :: ${excursion.id}")
            navigateToExcursion(excursion) }){
            Column ( modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .align(Alignment.CenterVertically))
            {
                Box {
                    ExcursionImage(excursion)
                    Box(modifier = Modifier.size(48.dp).align(Alignment.TopEnd)
                        .graphicsLayer {
                            clip = true
                            shape = RoundedCornerShape(15.dp)
                        }
                        .clickable {
                            onEvent(ExcursionsUiEvent.OnClickFavoriteExcursion(excursion))
                            Log.d("CLICK", "featured")
                        }
                    ) {
                        Image(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "featured",
                            colorFilter = ColorFilter.tint(Color.Gray.copy(alpha = .3f)),
                            modifier = Modifier.size(48.dp).align(Alignment.Center))
                        Image(
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = "featured",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier.size(48.dp).align(Alignment.Center))
                    }
                }
                Text(text = excursion.title, style = typography.headlineSmall,fontWeight= FontWeight.Bold)
                Text(text = excursion.description,  modifier = Modifier.height(24.dp),
                    style = typography.titleMedium)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ExcursionImage(excursion: Excursion) {
    val pagerState = rememberPagerState(initialPage = 0)

    HorizontalPager(
        count = excursion.images.size,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 0.dp),
        modifier = Modifier
            .heightIn(min=100.dp, max=200.dp)
            .fillMaxWidth()
    ) { page ->
        Box(modifier = Modifier.fillMaxWidth()
            .graphicsLayer {
                clip = true
                shape = RoundedCornerShape(16.dp)
            }) {
            NetworkImage(
                contentDescription = "",
                url = excursion.images[page].url,
                width = 350,
                height = 450
            )
        }

    }
}
