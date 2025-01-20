package com.example.GuideExpert.domain.models

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface FilterTypes {
    data object Sort: FilterTypes
    data object Categories: FilterTypes
    data object Duration: FilterTypes
    data object Groups: FilterTypes
}

@Stable
class Filter(
    val id: Int,
    val name: String,
    val type: FilterTypes,
    enabled: Boolean = false,
    val icon: ImageVector? = null
) {
    val enabled = mutableStateOf(enabled)
}
