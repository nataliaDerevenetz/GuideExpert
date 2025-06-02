package com.example.core.network.pojo

import com.google.gson.annotations.SerializedName

data class ProfileYandexPOJO(
    @SerializedName("id")
    val id: Int,

    @SerializedName("auth_token")
    val auth_token: String,

    @SerializedName("time")
    val time: Int,

    )
