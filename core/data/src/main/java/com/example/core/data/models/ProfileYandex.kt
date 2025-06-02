package com.example.core.data.models

import com.example.core.models.ProfileYandex
import com.example.core.network.pojo.ProfileYandexPOJO

fun ProfileYandexPOJO.toProfileYandex() = ProfileYandex(id, auth_token, time)
