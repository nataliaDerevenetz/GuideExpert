package com.example.GuideExpert.domain.models

import androidx.room.ColumnInfo

data class Image(
    val id:Int,
    val excursionId: Int,
    val url:String
)
