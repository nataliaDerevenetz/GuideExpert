package com.example.core.data.models

import com.example.core.models.Banner
import com.example.core.network.pojo.BannerPOJO

fun BannerPOJO.toBanner() = Banner(id, photo)
