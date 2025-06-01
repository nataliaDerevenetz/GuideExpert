package com.example.core.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Image(
    val id:Int,
    val excursionId: Int,
    val url:String
): Parcelable
