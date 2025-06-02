package com.example.core.data.models

import com.example.core.models.Config
import com.example.core.network.pojo.ConfigPOJO

fun ConfigPOJO.toConfig() = Config(banners.map { it.toBanner() })
