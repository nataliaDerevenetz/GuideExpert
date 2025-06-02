package com.example.core.network.pojo

import com.google.gson.annotations.SerializedName

data class ExcursionsFavoritePOJO (
    @SerializedName("excursions")
    val excursions: List<ExcursionPOJO>
)