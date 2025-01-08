package com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.GuideExpert.ui.theme.Shadow1
import com.example.GuideExpert.ui.theme.Shadow2

@Composable
fun ProfileBox(modifier: Modifier = Modifier,
    boxVisible: Boolean
){
    val gradient45 = Brush.linearGradient(
        colors = listOf(Shadow1, Shadow2),
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )
    AnimatedVisibility(visible = boxVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 1)),
        exit = fadeOut(animationSpec = tween(durationMillis = 1))) {
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
}