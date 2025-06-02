package com.example.core.network.pojo

import com.google.gson.annotations.SerializedName

data class ExcursionsPagingPOJO (
    @SerializedName("excursions")
    val excursions: List<ExcursionPOJO>,

    @SerializedName("nextOffset")
    val nextOffset: Int
)