package com.example.core.network.pojo

import com.google.gson.annotations.SerializedName

data class AvatarPOJO(
    @SerializedName("id")
    val id: Int,

    @SerializedName("profile_id")
    val profileId: Int,

    @SerializedName("url")
    val url: String,
)