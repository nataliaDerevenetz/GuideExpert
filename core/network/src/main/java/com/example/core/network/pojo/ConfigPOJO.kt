package com.example.core.network.pojo

import com.google.gson.annotations.SerializedName

data class ConfigPOJO(
    @SerializedName("banners")
    val banners: List<BannerPOJO>,
)
