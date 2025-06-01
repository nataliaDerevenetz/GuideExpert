package com.example.GuideExpert.data.models

import com.example.GuideExpert.data.remote.pojo.BannerPOJO
import com.example.core.models.Banner

fun BannerPOJO.toBanner() = Banner(id, photo)
