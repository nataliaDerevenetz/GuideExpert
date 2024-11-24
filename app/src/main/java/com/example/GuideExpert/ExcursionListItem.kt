package com.example.GuideExpert

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.GuideExpert.R
import com.example.GuideExpert.data.Excursion

@Composable
fun ExcursionListItem(excursion: Excursion, navigateToProfile: (Excursion) -> Unit) {
    Card(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp).fillMaxWidth(),
        /* colors = CardDefaults.cardColors(
             containerColor = Color.White, //Card background color
             contentColor = Color.DarkGray  //Card content color,e.g.text
         ),*/
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ){

        //  Log.e("TEST","row")
        Row( modifier = Modifier.clickable{
            Log.d("TAG", "clickable :: ${excursion.id}")
            navigateToProfile(excursion) }){
            Column ( modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .align(Alignment.CenterVertically))
            {
                Box {
                    ExcursionImage(excursion)
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.favorite_border) ,
                        contentDescription = "featured",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.align(Alignment.TopEnd)
                            .clickable { Log.d("CLICK","featured") }
                    )
                }
                Text(text = excursion.name, style = typography.headlineSmall)
                Text(text = "VIEW DETAIL", style = typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun ExcursionImage(excursion: Excursion) {
    Image(
        painter = painterResource(id = excursion.photo),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            //  .padding(8.dp)
            .fillMaxWidth()
            .heightIn(min=100.dp, max=200.dp)
            // .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}
