package com.example.core.network.pojo

import com.google.gson.annotations.SerializedName

data class RestoreFavoriteExcursionResponsePOJO(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("excursion")
    val excursion: com.example.core.network.pojo.ExcursionFavoritePOJO,
)
