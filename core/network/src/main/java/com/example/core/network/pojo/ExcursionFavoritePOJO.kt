package com.example.core.network.pojo

import com.google.gson.annotations.SerializedName

data class ExcursionFavoritePOJO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("excursionId")
    val excursionId: Int,
    @SerializedName("timestamp")
    val timestamp: Int,
)
