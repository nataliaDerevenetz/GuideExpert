package com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.GuideExpert.R
import com.example.GuideExpert.ui.theme.Shadow1

context(SharedTransitionScope, AnimatedVisibilityScope)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FilterScreen(
    onDismiss: () -> Unit
) {
   // var sortState by remember { mutableStateOf(SnackRepo.getSortDefault()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                // capture click
            }
    ) {

        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onDismiss()
                }
        )

        Column(
            Modifier
                .padding(16.dp)
                .align(Alignment.Center)
                .clip(MaterialTheme.shapes.medium)
                .sharedBounds(
                    rememberSharedContentState(FilterSharedElementKey),
                    animatedVisibilityScope = this@AnimatedVisibilityScope,
                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                    clipInOverlayDuringTransition = OverlayClip(MaterialTheme.shapes.medium)
                )
                .wrapContentSize()
                .heightIn(max = 450.dp)
                .verticalScroll(rememberScrollState())
                /*.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { }*/
                .background(MaterialTheme.colorScheme.onSecondary)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .skipToLookaheadSize(),
        ) {
            Row(modifier = Modifier.height(IntrinsicSize.Min).fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = ""
                    )
                }
                Text(
                    text = stringResource(id = R.string.label_filters),
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 8.dp, end = 48.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
                val resetEnabled = true//sortState != defaultFilter

                IconButton(
                    onClick = { /* TODO: Open search */ },
                    enabled = resetEnabled
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "",
                        tint = Shadow1.copy(alpha = if (!resetEnabled) 0.38f else 1f)
                    )

                }

            }

            Text("1")
            Text("1")
            Text("1")
            Text("1")
            Text("1")
            Text("1")
            Text("1")
            Text("1")
            Text("1")
            Text("1")
            Text("1")
            Text("1")
            Text("1")
            Text("1")
            Text("1")
            Text("1")
            Text("1")
            Text("1")
            Text("1")
            Text("1")



        }

    }

}