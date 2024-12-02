package com.example.GuideExpert.data

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.GuideExpert.serializableType
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf


@Serializable
@Parcelize
data class Excursion (
    val id: Int,
    val name: String,
    val description: String,
    val photo: Int
): Parcelable