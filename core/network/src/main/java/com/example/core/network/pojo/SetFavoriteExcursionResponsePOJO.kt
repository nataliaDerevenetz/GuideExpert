package com.example.core.network.pojo

import com.google.gson.annotations.SerializedName

data class SetFavoriteExcursionResponsePOJO(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("excursion")
    val excursion: ExcursionFavoritePOJO,
)
