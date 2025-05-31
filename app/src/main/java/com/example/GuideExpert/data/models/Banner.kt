package com.example.GuideExpert.data.models

import com.example.GuideExpert.data.remote.pojo.BannerPOJO
import com.example.GuideExpert.domain.models.Banner

fun BannerPOJO.toBanner() = Banner(id, photo)
