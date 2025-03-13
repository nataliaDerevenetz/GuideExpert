package com.example.GuideExpert.data.remote.pojo

import com.google.gson.annotations.SerializedName

data class UserYandexPOJO(
    @SerializedName("id")
    val id: Int,

    @SerializedName("login")
    val login: String,

    )
