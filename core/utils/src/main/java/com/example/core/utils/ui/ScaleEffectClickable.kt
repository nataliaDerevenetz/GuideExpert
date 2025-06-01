package com.example.core.utils.ui

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInteropFilter

fun Modifier.scaleEffectClickable(
    pressValue: Float = 0.75f,
    enabled: Boolean = true,
    onClick: () -> Unit
) =
    composed {
        val selected = remember { mutableStateOf(false) }
        val scale = animateFloatAsState(targetValue = if (selected.value) pressValue else 1f)
        this
            .scale(scale.value)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (enabled) {
                            selected.value = true
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        if (enabled) {
                            selected.value = false
                            onClick()
                        }
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        if (enabled) {
                            selected.value = false
                        }
                    }
                }
                true
            }

    }