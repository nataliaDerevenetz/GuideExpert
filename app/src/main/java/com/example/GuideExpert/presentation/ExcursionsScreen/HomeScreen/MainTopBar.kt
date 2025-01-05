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

            ProfileBox(boxVisible = boxVisible)

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