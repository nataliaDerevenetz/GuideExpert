package com.example.GuideExpert.data.models

import com.example.GuideExpert.data.remote.pojo.ConfigPOJO
import com.example.GuideExpert.domain.models.Config

fun ConfigPOJO.toConfig() = Config(banners.map { it.toBanner() })
