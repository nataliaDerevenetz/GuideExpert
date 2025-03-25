package com.example.GuideExpert.domain.models

import com.google.gson.annotations.SerializedName

data class AvatarUpdateResponse(
    val success: Boolean,
    val message: String,
    val imageUrl: String?,
    val idAvatar: Int?,
)