package com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.GuideExpert.domain.models.Excursion
import kotlin.math.roundToInt

@Composable
fun MainTopBar(modifier: Modifier = Modifier,
               snackbarHostState: SnackbarHostState,
               navigateToExcursion: (Excursion) -> Unit,
               toolbarHeightDp: Int,
               toolbarOffsetHeightPx: Float,
               scrollingOn:()->Unit,
               scrollingOff:()->Unit,
){

    var boxVisible by rememberSaveable { mutableStateOf(true) }




    Box(
        modifier =
        Modifier.height(toolbarHeightDp.dp).offset {
            IntOffset(x = 0, y = toolbarOffsetHeightPx.roundToInt())
        }.background(color = Color.White).fillMaxWidth()
    ){
        Column {

            val gradient45 = Brush.linearGradient(
                colors = listOf(Color.Blue, Color.Magenta),
                start = Offset(0f, Float.POSITIVE_INFINITY),
                end = Offset(Float.POSITIVE_INFINITY, 0f)
            )
            AnimatedVisibility(visible = boxVisible) {
                Box {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(horizontal = 3.dp, vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { Log.d("CLICK","Entre") }){
                            Icon(
                                Icons.Filled.AccountCircle, modifier = Modifier.size(48.dp)
                                    .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                                    .drawWithCache {
                                        onDrawWithContent {
                                            drawContent()
                                            drawRect(gradient45, blendMode = BlendMode.SrcAtop)
                                        }
                                    },
                                contentDescription = null,tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                "Войти",
                                fontSize = 16.sp,
                                color = Color.Blue,
                                textDecoration = TextDecoration.Underline
                            )
                        }
                            Icon( Icons.Filled.Notifications, modifier = Modifier.size(38.dp)
                                    .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                                    .drawWithCache {
                                        onDrawWithContent {
                                            drawContent()
                                            drawRect(gradient45, blendMode = BlendMode.SrcAtop)
                                        }
                                    },
                            contentDescription = null)
                    }
                }

            }

            ExcursionListSearchScreen(
                modifier = Modifier.fillMaxSize(),
                snackbarHostState = snackbarHostState,
                navigateToExcursion = navigateToExcursion,
                scrollingOn = scrollingOn,
                scrollingOff = scrollingOff,
                onActiveChanged = { boxVisible = !it }
            )
            HorizontalDivider(thickness=0.5.dp, modifier =  Modifier.shadow(3.dp))
        }
    }
}