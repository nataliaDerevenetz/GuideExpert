package com.example.core.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Serializable
@Parcelize
data class Excursion (
   // @SerialName("id")
    val id: Int,
    val title: String = "",
    val description: String = "",
    val images: List<Image> = listOf(),
    val idSort:Int = 0,
    var isFavorite :Boolean = false,
    val timestamp:Int = 0,
): Parcelable