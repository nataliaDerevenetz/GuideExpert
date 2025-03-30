package com.example.GuideExpert.data.remote.pojo

import com.google.gson.annotations.SerializedName

data class ExcursionsFavoriteIdResponsePOJO(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("excursions")
    val excursions: List<Int>,
)
