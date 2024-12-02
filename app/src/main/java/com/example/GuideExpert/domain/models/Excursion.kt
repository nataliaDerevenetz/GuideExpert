package com.example.GuideExpert.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
data class Excursion (
    val id: Int,
    val name: String,
    val description: String,
    val photo: Int
): Parcelable