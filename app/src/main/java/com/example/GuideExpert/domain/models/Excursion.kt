package com.example.GuideExpert.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
data class Excursion (
    val id: Int,
    val title: String,
    val description: String,
    val images: List<Image>,
    val idSort:Int,
    var isFavorite :Boolean = false
): Parcelable