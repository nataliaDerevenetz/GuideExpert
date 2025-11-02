package com.example.core.network.pojo

import com.google.gson.annotations.SerializedName

data class RegisterTokenDeviceResponsePOJO(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("timestamp")
    val timestamp: Int?,
)
